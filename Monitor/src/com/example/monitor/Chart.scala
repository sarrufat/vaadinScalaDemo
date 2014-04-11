package com.example.monitor

import org.vaadin.hezamu.canvas.Canvas

import com.vaadin.ui.CustomComponent

class Chart(width: Int, height: Int) extends CustomComponent {
  val canvas = new Canvas {
    setWidth(width + "px")
    setHeight(height + "px")
  }
  setCompositionRoot(canvas)
  def draw(s: Stock) {
    canvas.clear

    canvas.setFillStyle("cce9ff")
    for (idx <- 1 to 10 if idx % 2 == 1)
      canvas.fillRect(idx * width / 10, 0, width / 10, height)

    canvas.setFillStyle("black")
    canvas.setFont("20px Arial")
    canvas.fillText(s.name, 10, 30, width)
    canvas.setFont("15px Arial")
    canvas.fillText(s.price + "", 10, 50, width)
    canvas.setFillStyle("rgba(0,0,0,0.5)")
    val min = s.history.reduce((x, y) => if (x.price < y.price) x else y).price
    val max = s.history.reduce((x, y) => if (x.price > y.price) x else y).price
    var prevx = 0
    for (pp <- s.history) {
      val x = Math.round(width * (pp.time - s.history.head.time) / 60000)
      val y = width - width * (pp.price - min) / (max - min)
      canvas.fillRect(prevx, y, x - prevx, height - y)
      prevx = x
    }
  }
}
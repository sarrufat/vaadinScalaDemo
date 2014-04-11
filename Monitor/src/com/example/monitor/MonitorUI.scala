package com.example.monitor

import com.google.gwt.event.logical.shared.ValueChangeEvent
import com.vaadin.annotations.Theme
import com.vaadin.data.Property
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.Alignment
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Table
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
@Theme("monitor")
class MonitorUI extends UI {
  override def init(request: VaadinRequest) = {
    val content: VerticalLayout = new VerticalLayout
    content setHeight "100%"
    val hl = new HorizontalLayout() {
      setMargin(true)
      setSpacing(true)
      val chart = new Chart(512, 512)
      addComponent(new Table() {
        setSelectable(true)
        setImmediate(true)
        setHeight("100%")
        addContainerProperty("price", classOf[String], "")
        for (stock <- Market.stocks) addItem(Array("" + stock.price), stock.name)
        setRowHeaderMode(Table.RowHeaderMode.ID)
        setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN)
        setCacheRate(100)
        //        addValueChangeListener((e:Property.ValueChangeEvent) => chart.draw(Market.stocks.find(_.name == e.getProperty().getValue().toString)).first)
        val listener: Property.ValueChangeListener = new Property.ValueChangeListener {
          override def valueChange(e: Property.ValueChangeEvent) {
            val name = e.getProperty.getValue.toString
            chart.draw(Market.stocks.find(_.name == name).head)
          }
        }
        addValueChangeListener(listener)
      })
      addComponent(chart)
    }
    content addComponent hl
    content setComponentAlignment (hl, Alignment.MIDDLE_CENTER)
    setContent(content)
  }
}
package com.example.monitor

object Market {
	val stocks = for(s <- 1 to 200) yield Stock((for(i <- 1 to 4) yield ('A' + Math.random * 26).toChar).mkString)
}

case class Stock (name:String) {
  val history = randomHistory(System.currentTimeMillis()-60000, System.currentTimeMillis(), Math.random * 500)
  def price = Math.round(history.last.price * 100.0) / 100.0
 def randomHistory(start: Long, end: Long, price: Double) : List[PricePoint] = {
    if (start > end ) Nil 
    else PricePoint(price, start) :: randomHistory(start+1000, end, price *  ( Math.random / 10 + 0.95))
  }
}

case class PricePoint (price: Double, time: Long)
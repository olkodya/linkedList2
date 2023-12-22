import Fraction.isFractionValid

import java.util.{Comparator, Random}
import java.lang.Double
import scala.math.Ordered.orderingToOrdered


object Fraction {
  def isFractionValid(numerator: Int, denominator: Int): Boolean = {
    if (numerator >= denominator) return false
    denominator != 0
  }

  def create: UserType = {
    val r = new Random
    val randIntPart = r.nextInt(100) + 1
    var randNum = r.nextInt(100) + 1
    var ranDen = r.nextInt(100) + 1
    while (!isFractionValid(randNum, ranDen)) {
      randNum = r.nextInt(100) + 1
      ranDen = r.nextInt(100) + 1
    }
    val fraction = new Fraction(randIntPart, randNum, ranDen)
    fraction
  }

  //@Override
  def clone(obj: UserType): UserType = {
    val fraction = new Fraction
    fraction.setDenominator(obj.asInstanceOf[Fraction].getDenominator)
    fraction.setNumerator(obj.asInstanceOf[Fraction].getNumerator)
    fraction.setIntegerPart(obj.asInstanceOf[Fraction].getIntegerPart)
    fraction
  }
}

class Fraction extends UserType {
  integerPart = 1
  numerator = 1
  denominator = 2

  def this(integerPart: Int, numerator: Int, denominator: Int) {
    this()
    this.integerPart = integerPart
    this.numerator = numerator
    this.denominator = denominator
    if(!isFractionValid(numerator, denominator))
      throw new IllegalArgumentException("Invalid fraction")

  }


  override def typeName: String = String.valueOf(this.getClass)

  override def parseValue(ss: String): Fraction = {
    val parts = ss.split(" ")
    val frParts = parts(1).split("/")
    val fraction = new Fraction(parts(0).toInt, frParts(0).toInt, frParts(1).toInt)
    fraction
  }

  override def getTypeComparator: Comparator[UserType] = new Comparator[UserType]() {
    override def compare(o1: UserType, o2: UserType): Int = {
      val fraction1 = (o1.asInstanceOf[Fraction]).numerator.toDouble / o1.asInstanceOf[Fraction].denominator
      val fraction2 = (o2.asInstanceOf[Fraction]).numerator.toDouble / o2.asInstanceOf[Fraction].denominator
      if (o1.asInstanceOf[Fraction].integerPart != o2.asInstanceOf[Fraction].integerPart) return o1.asInstanceOf[Fraction].integerPart - o2.asInstanceOf[Fraction].integerPart
      else return Double.compare(fraction1, fraction2)
    }
  }

  def getNumerator: Int = numerator

  def setNumerator(numerator: Int): Unit = {
    if (Fraction.isFractionValid(numerator, denominator)) this.numerator = numerator
    else throw new IllegalArgumentException("Invalid fraction")
  }

  def setDenominator(denominator: Int): Unit = {
    if (Fraction.isFractionValid(numerator, denominator)) this.denominator = denominator
    else throw new IllegalArgumentException("Invalid fraction")
  }

  def setIntegerPart(integerPart: Int): Unit = {
    this.integerPart = integerPart
  }

  def getDenominator: Int = denominator

  def getIntegerPart: Int = integerPart

  override def toString: String = "{" + integerPart + " " + numerator + "/" + denominator + "}"

  private var numerator = 0
  private var denominator = 0
  private var integerPart = 0
}
import java.util.Comparator
import java.util.Random


object MyInteger {
  def create: UserType = {
    val r = new Random
    val randValue = r.nextInt(100) + 1
    val myInteger = new MyInteger(randValue)
    myInteger
  }

  def clone(obj: UserType): UserType = {
    val myInteger = new MyInteger
    myInteger.value = obj.asInstanceOf[MyInteger].value
    myInteger
  }
}

class MyInteger extends UserType {
  override def typeName: String = String.valueOf(this.getClass)

  def this(value: Integer) {
    this()
    this.value = value
  }

  override def parseValue(ss: String) = new MyInteger(ss.toInt)

  override def getTypeComparator: Comparator[UserType] = new Comparator[UserType]() {
    override def compare(o1: UserType, o2: UserType): Int = o1.asInstanceOf[MyInteger].value - o2.asInstanceOf[MyInteger].value
  }

  def setValue(value: Integer): Unit = {
    this.value = value
  }

  def getValue: Integer = value

  override def toString: String = String.valueOf(value)

  private var value: Integer = null
}
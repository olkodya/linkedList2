
import java.util.Comparator


object UserType {
  def create: Any = null

  def clone(obj: Any): Any = null
}

trait UserType extends Serializable {
  def typeName: String

  def parseValue(ss: String): UserType

  def getTypeComparator: Comparator[UserType]
}
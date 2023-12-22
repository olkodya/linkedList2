import java.io.Serializable


class Node(data1: UserType) extends Serializable {
  private var data: UserType = data1
  private var next: Node = null
  def getNext: Node = next

  def getData: UserType = data

  def setData(data:UserType): Unit = {
    this.data = data
  }

  def setNext(next: Node): Unit = {
    this.next = next
  }
}
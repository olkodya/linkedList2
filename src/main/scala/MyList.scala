import java.io._
import java.util.Comparator


object MyList extends  Serializable {
  @throws[IOException]
  def serializeToBinary(list: MyList, fileName: String): Unit = {
    val fos = new FileOutputStream(fileName)
    val oos = new ObjectOutputStream(fos)
    oos.writeObject(list)
    oos.flush()
    oos.close()
  }

  @throws[IOException]
  @throws[ClassNotFoundException]
  def deserializeFromBinary(fileName: String): MyList = {
    val fis = new FileInputStream(fileName)
    val oin = new ObjectInputStream(fis)
    oin.readObject.asInstanceOf[MyList]
  }
}

class MyList extends Serializable {
  def add(ob: UserType): Unit = {
    var cur = head
    if (head == null) {
      head = new Node(ob)
      size += 1
      return
    }
    while (cur.getNext != null) cur = cur.getNext
    val node = new Node(ob)
    size += 1
    cur.setNext(node)
  }

  def isEmpty: Boolean = size == 0

  def clear (): Unit = {
    while (!this.isEmpty) {
      remove(0)
    }
  }

  def get(index: Int): Node = {
    if (index < 0 || index >= size) throw new IllegalArgumentException("Invalid index " + index)
    var cur = head
    for (i <- 0 until index) {
      cur = cur.getNext
    }
    cur
  }

  def add(ob: UserType, index: Int): Unit = {
    if (index < 0 || index > size) throw new IllegalArgumentException("Invalid index " + index)
    if (index == size) {
      add(ob)
      return
    }
    var cur = head
    var prev = head
    for (i <- 0 until index) {
      prev = cur
      cur = cur.getNext
    }
    val node = new Node(ob)
    if (prev eq cur) {
      head = node
      node.setNext(cur)
      size += 1
      return
    }
    size += 1
    prev.setNext(node)
    node.setNext(cur)
  }

  def remove(index: Int): UserType = {
    if (index < 0 || index >= size) throw new IllegalArgumentException("Invalid index " + index)
    var cur = head
    var prev = head
    for (i <- 0 until index) {
      prev = cur
      cur = cur.getNext
    }
    if (cur eq prev) head = head.getNext
    val ob = cur.getData
    prev.setNext(cur.getNext)
    size -= 1
    ob
  }

  def getSize: Int = size

  def swap(a: Node, aPrev: Node, b: Node, bPrev: Node): Unit = {
    if (a.getNext == b) {
      a.setNext(b.getNext)
      b.setNext(a)
      if (aPrev != null) aPrev.setNext(b)
      else head = b
    }
    else {
      val tmp = a.getNext
      a.setNext(b.getNext)
      b.setNext(tmp)
      if (aPrev != null) aPrev.setNext(b)
      else head = b
      if (bPrev != null) bPrev.setNext(a)
    }
  }

  def partition(start: Int, end: Int, comparator: Comparator[UserType]): Int = {
    val pivot = get(end)
    var prev: Node = null
    var pPrev: Node = null
    var pivotPrev: Node = null
    if (end - 1 < 0) pivotPrev = null
    else pivotPrev = get(end - 1)
    if (start - 1 < 0) pPrev = null
    else pPrev = get(start - 1)
    var cur = get(start)
    var pIndex = start
    var p = get(pIndex)
    if (start - 1 < 0) prev = null
    else prev = get(start - 1)
    while (cur ne pivot) if (comparator.compare(cur.getData, pivot.getData) <= 0) {
      swap(p, pPrev, cur, prev)
      if (cur eq pivotPrev) pivotPrev = p
      val tmp = p
      pPrev = cur
      p = cur.getNext
      pIndex += 1
      cur = tmp.getNext
      prev = tmp
    }
    else {
      prev = cur
      cur = cur.getNext
    }
    swap(p, pPrev, pivot, pivotPrev)
    // }
    pIndex
  }

  def quickSort(start: Int, end: Int, comparator: Comparator[UserType]): Unit = {
    if (start >= end) return
    val pivot = partition(start, end, comparator)
    quickSort(start, pivot - 1, comparator)
    quickSort(pivot + 1, end, comparator)
  }

  override def toString: String = {
    var string = ""
    var cur = head
    if (size == 0) {
      return "List is empty"
    }
    while (cur != null) {
      string += (cur.getData + " ")
      cur = cur.getNext
    }
    string
  }

  def forEach(callBackInt: CallBack): Unit = {
    var cur = head
    while (cur != null) {
      callBackInt.toDo(cur.getData)
      cur = cur.getNext
    }
  }



  private var head: Node = null
  private var size = 0
}
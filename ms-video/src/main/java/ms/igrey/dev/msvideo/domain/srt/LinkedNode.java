package ms.igrey.dev.msvideo.domain.srt;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Accessors(fluent = true)
public class LinkedNode<T> {
    private T elm;
    private LinkedNode<T> previous;
    private LinkedNode<T> next;
    private LinkedNode<T> first;

    public LinkedNode(List<T> list) {
        this.first = new LinkedNode<>(list.get(0), null, null, null);
        this.elm = list.get(0);
        LinkedNode<T> current = this.first;
        for (int i = 1; i < list.size(); i++) {
            current.setNext(new LinkedNode<>(list.get(i), current, null, this.first));
            current = current.next();
        }
        this.setNext(this.first.next);
    }

    public LinkedNode(T elm, LinkedNode<T> previous, LinkedNode<T> next, LinkedNode<T> first) {
        this.elm = elm;
        this.previous = previous;
        this.next = next;
        this.first = first;
    }

    public static void main(String[] args) {
        LinkedList<Integer> list = Stream.of(1, 101, 3, 102, 5, 103, 104, 8, 9, 6, 4, 122, 4).collect(Collectors.toCollection(LinkedList::new));
        LinkedNode<Integer> node = new LinkedNode<>(list);
        System.out.println(list);
        System.out.println(node.list());

        do {
            if (node.elm() < 10) {
                node = node.remove();
                System.out.println(node.list());
            } else {
                node = node.next();
            }
        } while (node.hasNext());
        System.out.println(node.list());
    }

    /**
     * удаляет текущий, переписывая ссылки. return предыдущий, если он есть
     */
    public LinkedNode<T> remove() {
        if (this.previous == null && this.next == null) {
            return this;
        }
        if (this.previous != null) {
            LinkedNode<T> previous = this.previous;
            previous.setNext(next);
            if (this.next != null) this.next.setPrevious(previous);
            this.previous = null;
            this.next = null;
            this.first = null;
            this.elm = null;
            return previous;
        } else { // if remove first
            return removeFirst();
        }
    }

    LinkedNode<T> removeFirst() {
        LinkedNode<T> newFirst = this.next;
        newFirst.setPrevious(null);
        this.first = null;
        this.previous = null;
        this.next = null;
        this.elm = null;
        LinkedNode<T> current = newFirst;
        do {
            current.setFirst(newFirst);
            current = current.next;
        } while (current != null);
        return newFirst;
    }

    public List<T> list() {
        if (first == null) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        LinkedNode<T> current = first;
        do {
            result.add(current.elm);
            current = current.next;
        } while (current != null);
        return result;
    }

    public void setElm(T elm) {
        this.elm = elm;
    }

    public void setPrevious(LinkedNode<T> previous) {
        this.previous = previous;
    }

    public void setNext(LinkedNode<T> next) {
        this.next = next;
    }

    public void setFirst(LinkedNode<T> first) {
        this.first = first;
    }

    public boolean hasNext() {
        return next != null;
    }
}

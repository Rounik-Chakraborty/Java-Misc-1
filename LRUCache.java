import java.util.*;

public class LRUCache<K, V> {
    private final int capacity;
    private final Map<K, Node> cache;
    private final DoublyLinkedList dll;

    private class Node {
        K key;
        V value;
        Node prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private class DoublyLinkedList {
        private Node head, tail;

        DoublyLinkedList() {
            head = new Node(null, null);
            tail = new Node(null, null);
            head.next = tail;
            tail.prev = head;
        }

        void addFirst(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        Node removeLast() {
            Node last = tail.prev;
            if (last == head) {
                return null;
            }
            remove(last);
            return last;
        }
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        dll = new DoublyLinkedList();
    }

    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        Node node = cache.get(key);
        dll.remove(node);
        dll.addFirst(node);
        return node.value;
    }

    public void put(K key, V value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            dll.remove(node);
            node.value = value;
            dll.addFirst(node);
        } else {
            if (cache.size() == capacity) {
                Node last = dll.removeLast();
                cache.remove(last.key);
            }
            Node newNode = new Node(key, value);
            dll.addFirst(newNode);
            cache.put(key, newNode);
        }
    }

    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        System.out.println(cache.get(1)); // one
        cache.put(4, "four");
        System.out.println(cache.get(2)); // null (evicted)
    }
}

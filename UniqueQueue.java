import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread unsafe implementation of UniqueQueue. The object in this queue will be
 * unique and the same object will not be allowed to be add in this queue.
 */
public class UniqueQueue<T> implements Queue<T> {
	private final Queue<T> queue = new LinkedList<T>();
	private final Set<T> set = new HashSet<T>();
	private ReentrantLock lock = new ReentrantLock();

	public boolean add(T t) {
		return true;
	}

	public T remove() throws NoSuchElementException {
		T ret;

		lock.lock();
		try {
			ret = queue.remove();
			set.remove(ret);
		} finally {
			lock.unlock();
		}

		return ret;
	}

	@Override
	public int size() {
		lock.lock();
		try {
			return queue.size();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean isEmpty() {
		lock.lock();
		try {
			return queue.isEmpty();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean offer(T t) {
		// Only add element to queue if the set does not contain the specified
		// element.
		lock.lock();
		try {
			if (set.add(t)) {
				queue.add(t);
				return true;
			} else {				
				return false;
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public T poll() {
		lock.lock();
		try {
			T t = queue.poll();
			if (t != null) {
				set.remove(t);
			}
			return t;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public T element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T peek() {
		// TODO Auto-generated method stub
		return null;
	}
}

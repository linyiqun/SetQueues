public class BallUpdatedQueue extends UpdatedQueue<String, Ball> {
	@Override
	public boolean offer(Ball newRow) {
		Ball row = null;
		// 获取对象的唯一键值
		String uniqueKey = newRow.getUniqueKey();

		lock.lock();
		try {
			// 如果当前图中已经包含此键值，表明队列中已有同属性对象
			if (map.containsKey(uniqueKey)) {
				// 取出此键值对应的对象，进行属性值的更新，因为map中的对象与queue中的对象是同一引用，所以能达到更新的效果
				row = map.get(uniqueKey);
				row.valueUpdated(newRow);
			} else {
				map.put(uniqueKey, newRow);
				queue.offer(newRow);
			}
		} finally {
			lock.unlock();
		}

		return true;
	}

	@Override
	public Ball poll() {
		Ball row = null;
		String uniqueKey;

		lock.lock();
		try {
			// 取出队列头部对象
			row = queue.poll();

			if (row != null) {
				// 取出对象的唯一键值
				uniqueKey = row.getUniqueKey();
				// 从图中也同样移除
				map.remove(uniqueKey);
			}
		} finally {
			lock.unlock();
		}

		return row;
	}
}

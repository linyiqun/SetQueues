public class BallUpdatedQueue extends UpdatedQueue<String, Ball> {
	@Override
	public boolean offer(Ball newBall) {
		Ball ball = null;
		// 获取对象的唯一键值
		String uniqueKey = newBall.getUniqueKey();

		lock.lock();
		try {
			// 如果当前图中已经包含此键值，表明队列中已有同属性对象
			if (map.containsKey(uniqueKey)) {
				// 取出此键值对应的对象，进行属性值的更新，因为map中的对象与queue中的对象是同一引用，所以能达到更新的效果
				ball = map.get(uniqueKey);
				ball.valueUpdated(newBall);
			} else {
				map.put(uniqueKey, newBall);
				queue.offer(newBall);
			}
		} finally {
			lock.unlock();
		}

		return true;
	}

	@Override
	public Ball poll() {
		Ball ball = null;
		String uniqueKey;

		lock.lock();
		try {
			// 取出队列头部对象
			ball = queue.poll();

			if (ball != null) {
				// 取出对象的唯一键值
				uniqueKey = ball.getUniqueKey();
				// 从图中也同样移除
				map.remove(uniqueKey);
			}
		} finally {
			lock.unlock();
		}

		return ball;
	}
}

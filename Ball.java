public class Ball {
	// 球类的名称
	String ballName;
	// 球的单价
	int price;
	// 球的销售量
	int numSale;
	
	public Ball(String ballName, int price, int numSale) {
		this.ballName = ballName;
		this.price = price;
		this.numSale = numSale;
	}

	public void valueUpdated(Ball newBall) {
		this.price = newBall.price;
		this.numSale = newBall.numSale;
	}

	public String getUniqueKey() {
		return this.ballName;
	}

	@Override
	public String toString() {
		return "Ball [ballName=" + ballName + ", price=" + price + ", numSale=" + numSale + "]";
	}
}

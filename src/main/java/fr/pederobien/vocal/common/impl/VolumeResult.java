package fr.pederobien.vocal.common.impl;

public class VolumeResult {
	public static final VolumeResult NONE = new VolumeResult(0);
	public static final VolumeResult DEFAULT = new VolumeResult(1.0, 1.0, 1.0);
	private double global, left, right;

	public VolumeResult(double global, double left, double right) {
		this.global = Math.max(0, global);
		this.left = Math.max(0, left);
		this.right = Math.max(0, right);
	}

	public VolumeResult(double global) {
		this(global, 1.0, 1.0);
	}

	/**
	 * @return The global volume for the left and right channel.
	 */
	public double getGlobal() {
		return global;
	}

	/**
	 * @return The volume for the left audio channel.
	 */
	public double getLeft() {
		return left;
	}

	/**
	 * @return The volume for the right audio channel.
	 */
	public double getRight() {
		return right;
	}

	@Override
	public String toString() {
		return String.format("VolumeResult={global=%.4f, left=%.4f, right=%.4f}", global, left, right);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof VolumeResult))
			return false;

		VolumeResult other = (VolumeResult) obj;
		return global == other.getGlobal() && left == other.getLeft() && right == other.getRight();
	}
}
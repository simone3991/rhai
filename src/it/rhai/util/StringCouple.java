package it.rhai.util;

/**
 * A simple couple of {@link String} instances
 * 
 * @author simone
 *
 */
public final class StringCouple implements Comparable<StringCouple> {
	private String string1;
	private String string2;

	/**
	 * Creates a new couple object
	 * 
	 * @param string1
	 *            : the first element of the couple
	 * @param string2
	 *            : the second one
	 */
	public StringCouple(String string1, String string2) {
		this.string1 = string1;
		this.string2 = string2;
	}

	public String getFirst() {
		return string1;
	}

	public String getSecond() {
		return string2;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return obj instanceof StringCouple ? ((StringCouple) obj).string1
				.equals(string1)
				&& ((StringCouple) obj).string2.equals(string2) : false;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(StringCouple other) {
		int cmp = string1.compareTo(other.string1);
		return cmp == 0 ? cmp : string2.compareTo(other.string2);
	}
}
package it.rhai.settings;

import java.io.PrintStream;

public enum DebugPrinter {
	console(System.out),
	stderr(System.err);

	private PrintStream printer;

	private DebugPrinter(PrintStream printer) {
		this.printer = printer;
	}
	
	public PrintStream getPrinter() {
		return printer;
	}

}

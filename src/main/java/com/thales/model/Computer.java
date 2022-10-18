package com.thales.model;

public class Computer {
	
	private int memory;
	private String brand;
	private int processor;
	
	public Computer(int memory, String brand, int processor) {
		super();
		this.memory = memory;
		this.brand = brand;
		this.processor = processor;
	}
	
	public int getMemory() {
		return memory;
	}
	public void setMemory(int memory) {
		this.memory = memory;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getProcessor() {
		return processor;
	}
	public void setProcessor(int processor) {
		this.processor = processor;
	}
	
	
	
}

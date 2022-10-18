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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + memory;
		result = prime * result + processor;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (memory != other.memory)
			return false;
		if (processor != other.processor)
			return false;
		return true;
	}
	
	
}

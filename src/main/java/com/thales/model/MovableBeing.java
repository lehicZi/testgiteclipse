package com.thales.model;

import java.io.Serializable;
import java.util.Objects;

import Exception.BeingIsDeadException;
import Exception.CoordAlreadyUsedException;
import Exception.EnemyTooCloseException;
import Exception.OutOfRangeException;
import Exception.OutofMapException;
import lombok.NoArgsConstructor;


@SuppressWarnings("serial")
@NoArgsConstructor
public class MovableBeing extends Being implements Movable,Serializable {

	protected int maxSpeed;
	protected int force;
	protected boolean inAir=false;



	public MovableBeing(String name, int x, int y, int maxHealth, int health, double resistance,int maxSpeed, int force, boolean inAir) {
		super(name, x, y, maxHealth, health, resistance);
		this.maxSpeed = maxSpeed;
		this.force = force;
		this.inAir = inAir;
	}

	@Override
	public void goUp(int speed, Map map) throws OutofMapException, CoordAlreadyUsedException  {
		int xInit=this.getX();
		int yInit=this.getY();
		if (speed>maxSpeed) 
			speed=maxSpeed;

		if (this.y+speed<41) {
			this.y+= speed;
			for ( SpacialElement spacialeElement : map.getSpacialElements()) {
				if (spacialeElement.getX()==this.x && spacialeElement.getY()==this.y &&!spacialeElement.equals(this))
				{
					this.x=xInit;
					this.y=yInit;
					throw new CoordAlreadyUsedException(getName()+" marche sur quelqu'un");
				}
			}
		}
		else throw new OutofMapException(getName()+" tu sors de la carte");
	}


	public void goDown(int speed, Map map) throws OutofMapException, CoordAlreadyUsedException {
		int xInit=this.getX();
		int yInit=this.getY();
		if (speed>maxSpeed) 
			speed=maxSpeed;

		if (this.y-speed>0) {
			this.y+= speed;
			for ( SpacialElement spacialeElement : map.getSpacialElements()) {
				if (spacialeElement.getX()==this.x && spacialeElement.getY()==this.y &&!spacialeElement.equals(this))
				{
					this.x=xInit;
					this.y=yInit;
					throw new CoordAlreadyUsedException(getName()+" marche sur quelqu'un");
				}
			}
		}
		else throw new OutofMapException(getName()+" tu sors de la carte");
	}

	public void goRight(int speed, Map map) throws OutofMapException, CoordAlreadyUsedException {
		int xInit=this.getX();
		int yInit=this.getY();
		if (speed>maxSpeed) 
			speed=maxSpeed;

		if (this.x+speed<41) {
			this.x+= speed;
			for ( SpacialElement spacialeElement : map.getSpacialElements()) {
				if (spacialeElement.getX()==this.x && spacialeElement.getY()==this.y &&!spacialeElement.equals(this))
				{
					this.x=xInit;
					this.y=yInit;
					throw new CoordAlreadyUsedException(getName()+" marche sur quelqu'un");
				}
			}
		}
		else throw new OutofMapException(getName()+" tu sors de la carte");
	}

	public void goLeft(int speed, Map map) throws OutofMapException, CoordAlreadyUsedException {
		int xInit=this.getX();
		int yInit=this.getY();
		if (speed>maxSpeed) 
			speed=maxSpeed;

		if (this.x-speed>0) {
			this.x-= speed;
			for ( SpacialElement spacialeElement : map.getSpacialElements()) {
				if (spacialeElement.getX()==this.x && spacialeElement.getY()==this.y &&!spacialeElement.equals(this))
				{
					this.x=xInit;
					this.y=yInit;
					throw new CoordAlreadyUsedException(getName()+" marche sur quelqu'un");
				}
			}
		}
		else throw new OutofMapException(getName()+" tu sors de la carte");
	}

	
	public int distanceTarget (Being target) {
		return Math.max(Math.abs(this.getX()-target.getX()),Math.abs(this.getY()-target.getY()));
	}

	//je pense qu'il y avait plus simple
	public void attack (Being target) throws EnemyTooCloseException, OutOfRangeException, BeingIsDeadException {
		//cas 1: la cible est un dragon
		if (target instanceof Dragon) {
			//cas 1.1: le dragon est en vol
			if ( ((MovableBeing) target).isInAir() ) {
				//Cas 1.1.1: un dragon attaque un dragon
				if (this instanceof Dragon && this.inAir==true){
					if (this.distanceTarget(target)<=((Dragon) this).getRange()) 
						target.recieveDamage(((Dragon) this).getPower());
					else throw new OutOfRangeException(getName()+" Target out of range");
				}
				//Cas 1.1.2: un archer attaque un dragon
				else if (this instanceof Archer) {
					if (this.distanceTarget(target)<=((Archer) this).getRange()) 
						target.recieveDamage(((Archer) this).getDexterity());
					else throw new OutOfRangeException(getName()+" Target out of range");
				}
				//Cas 1.1.3: un eagle attaque un dragon
				else if (this instanceof Eagle && this.inAir==true) {
					if (this.distanceTarget(target)<=1) 
						target.recieveDamage(((Eagle) this).getForce());
					else throw new OutOfRangeException(getName()+" Target out of range");
				}
				//Cas 1.1.4: Autre (attaque de mélé)
				else throw new OutOfRangeException(getName()+" ne peut pas attaquer une cible en vol");
			}
			//un dragon se fait attaqué au sol
			//1 par un dragon
			else if (this instanceof Dragon) {
				if (this.distanceTarget(target)<=((Dragon) this).getRange()&&this.distanceTarget(target)>1) 
					target.recieveDamage(((Dragon) this).getPower());
				else if(this.distanceTarget(target)==1) {
					target.recieveDamage(((Dragon) this).getForce());
				}
				else throw new OutOfRangeException(getName()+" Target out of range");
			}//2 par un archer
			else if (this instanceof Archer) {
				if (this.distanceTarget(target)<=((Archer) this).getRange()&&this.distanceTarget(target)>1) 
					target.recieveDamage(((Archer) this).getDexterity());
				else if(this.distanceTarget(target)==1) {
					target.recieveDamage(((Archer) this).getForce());
				}
				else throw new OutOfRangeException(getName()+" Target out of range");

			}//2 par autre chose
			else if(this.distanceTarget(target)==1) {
				target.recieveDamage(((MovableBeing) this).getForce());
			}
			else throw new OutOfRangeException(getName()+" Target out of range");
		}
		//2e cas volant: l'aigle
		else if (target instanceof Eagle) {
			//cas 1.1: le dragon est en vol
			if ( ((MovableBeing) target).isInAir() ) {
				//Cas 1.1.1: un dragon attaque un aigle
				if (this instanceof Dragon){
					if (this.distanceTarget(target)<=((Dragon) this).getRange()) 
						target.recieveDamage(((Dragon) this).getPower());
					else throw new OutOfRangeException(getName()+" Target out of range");
				}
				//Cas 1.1.2: un archer attaque un aigle
				else if (this instanceof Archer) {
					if (this.distanceTarget(target)<=((Archer) this).getRange()) 
						target.recieveDamage(((Archer) this).getDexterity());
					else throw new OutOfRangeException(getName()+" Target out of range");
				}
				//Cas 1.1.3: un eagle attaque un aigle
				else if (this instanceof Eagle && this.inAir==true) {
					if (this.distanceTarget(target)<=1) 
						target.recieveDamage(((Eagle) this).getForce());
					else throw new OutOfRangeException(getName()+" Target out of range");
				}
				//Cas 1.1.4: Autre 
				else throw new OutOfRangeException(getName()+" ne peut pas attaquer une cible en vol");
			}
			//un aigle se fait attaqué au sol
			//1 par un dragon
			else if (this instanceof Dragon) {
				if (this.distanceTarget(target)<=((Dragon) this).getRange()&&this.distanceTarget(target)>1) 
					target.recieveDamage(((Dragon) this).getPower());
				else if(this.distanceTarget(target)==1) {
					target.recieveDamage(((Dragon) this).getForce());
				}
				else throw new OutOfRangeException(getName()+" Target out of range");
			}//2 par un archer
			else if (this instanceof Archer) {
				if (this.distanceTarget(target)<=((Archer) this).getRange()&&this.distanceTarget(target)>1) 
					target.recieveDamage(((Archer) this).getDexterity());
				else if(this.distanceTarget(target)==1) {
					target.recieveDamage(((Archer) this).getForce());
				}
				else throw new OutOfRangeException(getName()+" Target out of range");

			}//2 par autre chose (attaque de mélé)
			else if(this.distanceTarget(target)==1) {
				target.recieveDamage(((MovableBeing) this).getForce());
			}
			else throw new OutOfRangeException(getName()+" Target out of range");

		}
		//dernier cas target non volante
		else {
			if (this instanceof Archer) {
				if (this.distanceTarget(target)<=((Archer) this).getRange()) 
					target.recieveDamage(((Archer) this).getDexterity());
				else throw new OutOfRangeException(getName()+" Target out of range");
			}

			else if (this instanceof Dragon) {
				if (this.distanceTarget(target)<=((Dragon) this).getRange()&&this.distanceTarget(target)>1) 
					target.recieveDamage(((Dragon) this).getPower());
				else if(this.distanceTarget(target)==1) {
					target.recieveDamage(((Dragon) this).getForce());
				}
				else throw new OutOfRangeException(getName()+" Target out of range");
			}

			//2 par autre chose
			else if(this.distanceTarget(target)==1) {
				target.recieveDamage(((MovableBeing) this).getForce());
			}
			else throw new OutOfRangeException(getName()+" Target out of range");
		}
	}


	//A faire 
	/*
	public void attacker (Pack target) {
	}
	 */

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	public boolean isInAir() {
		return inAir;
	}

	public void setInAir(boolean inAir) {
		this.inAir = inAir;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovableBeing other = (MovableBeing) obj;
		return force == other.force && inAir == other.inAir && maxSpeed == other.maxSpeed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(force, inAir, maxSpeed);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MovableBeing [maxSpeed=");
		builder.append(maxSpeed);
		builder.append(", force=");
		builder.append(force);
		builder.append(", inAir=");
		builder.append(inAir);
		builder.append(", health=");
		builder.append(health);
		builder.append(", resistance=");
		builder.append(resistance);
		builder.append(", x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", getName()=");
		builder.append(getName());
		builder.append("]");
		return builder.toString();
	}




}

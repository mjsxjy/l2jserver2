//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.25 at 01:03:28 PM BRT 
//

package com.l2jserver.model.template;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Coordinate complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Coordinate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="x" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="y" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="z" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Coordinate")
public class Coordinate {

	@XmlAttribute(name = "x", required = true)
	protected int x;
	@XmlAttribute(name = "y", required = true)
	protected int y;
	@XmlAttribute(name = "z", required = true)
	protected int z;

	/**
	 * Gets the value of the x property.
	 * 
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the value of the x property.
	 * 
	 */
	public void setX(int value) {
		this.x = value;
	}

	/**
	 * Gets the value of the y property.
	 * 
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the value of the y property.
	 * 
	 */
	public void setY(int value) {
		this.y = value;
	}

	/**
	 * Gets the value of the z property.
	 * 
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Sets the value of the z property.
	 * 
	 */
	public void setZ(int value) {
		this.z = value;
	}

}
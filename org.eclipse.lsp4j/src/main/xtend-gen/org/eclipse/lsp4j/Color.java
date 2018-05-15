/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents a color in RGBA space.
 */
@SuppressWarnings("all")
public class Color {
  /**
   * The red component of this color in the range [0-1].
   */
  private double red;
  
  /**
   * The green component of this color in the range [0-1].
   */
  private double green;
  
  /**
   * The blue component of this color in the range [0-1].
   */
  private double blue;
  
  /**
   * The alpha component of this color in the range [0-1].
   */
  private double alpha;
  
  public Color() {
  }
  
  public Color(final double red, final double green, final double blue, final double alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }
  
  /**
   * The red component of this color in the range [0-1].
   */
  @Pure
  public double getRed() {
    return this.red;
  }
  
  /**
   * The red component of this color in the range [0-1].
   */
  public void setRed(final double red) {
    this.red = red;
  }
  
  /**
   * The green component of this color in the range [0-1].
   */
  @Pure
  public double getGreen() {
    return this.green;
  }
  
  /**
   * The green component of this color in the range [0-1].
   */
  public void setGreen(final double green) {
    this.green = green;
  }
  
  /**
   * The blue component of this color in the range [0-1].
   */
  @Pure
  public double getBlue() {
    return this.blue;
  }
  
  /**
   * The blue component of this color in the range [0-1].
   */
  public void setBlue(final double blue) {
    this.blue = blue;
  }
  
  /**
   * The alpha component of this color in the range [0-1].
   */
  @Pure
  public double getAlpha() {
    return this.alpha;
  }
  
  /**
   * The alpha component of this color in the range [0-1].
   */
  public void setAlpha(final double alpha) {
    this.alpha = alpha;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("red", this.red);
    b.add("green", this.green);
    b.add("blue", this.blue);
    b.add("alpha", this.alpha);
    return b.toString();
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Color other = (Color) obj;
    if (Double.doubleToLongBits(other.red) != Double.doubleToLongBits(this.red))
      return false; 
    if (Double.doubleToLongBits(other.green) != Double.doubleToLongBits(this.green))
      return false; 
    if (Double.doubleToLongBits(other.blue) != Double.doubleToLongBits(this.blue))
      return false; 
    if (Double.doubleToLongBits(other.alpha) != Double.doubleToLongBits(this.alpha))
      return false; 
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (Double.doubleToLongBits(this.red) ^ (Double.doubleToLongBits(this.red) >>> 32));
    result = prime * result + (int) (Double.doubleToLongBits(this.green) ^ (Double.doubleToLongBits(this.green) >>> 32));
    result = prime * result + (int) (Double.doubleToLongBits(this.blue) ^ (Double.doubleToLongBits(this.blue) >>> 32));
    return prime * result + (int) (Double.doubleToLongBits(this.alpha) ^ (Double.doubleToLongBits(this.alpha) >>> 32));
  }
}

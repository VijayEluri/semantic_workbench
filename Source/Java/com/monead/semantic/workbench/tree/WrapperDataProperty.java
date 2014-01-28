package com.monead.semantic.workbench.tree;

/**
 * 
 * Wraps a data property name in a tree.
 * 
 * The wrapper is used by the SemanticWorkbench tree view and
 * the OntologyTreeCellRenderer in order for the correct
 * tree node icon to be chosen when rendering this value
 * as a node in the tree.
 * 
 * Copyright (C) 2010-2014 David S. Read
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author David Read
 *
 */
public class WrapperDataProperty {
  String label;

  public WrapperDataProperty(String label) {
    this.label = label;
  }

  public String toString() {
    return label;
  }
}
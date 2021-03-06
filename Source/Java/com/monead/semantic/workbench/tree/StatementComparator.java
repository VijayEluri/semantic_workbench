package com.monead.semantic.workbench.tree;

import java.util.Comparator;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Statement;

/**
 * Compare two statements for sorting based on the predicate name. Non-anonymous
 * statements are order before anonymnous ones.
 * 
 * @author David Read
 * 
 */
public class StatementComparator implements Comparator<Statement> {

  /**
   * No operation
   */
  public StatementComparator() {

  }

  @Override
  public int compare(Statement s1, Statement s2) {
    Property s1Predicate;
    Property s2Predicate;
    String s1Label;
    String s2Label;
    int compareResult;

    s1Predicate = s1.getPredicate();
    s2Predicate = s2.getPredicate();

    // Anonymous statements go below named statements
    if (!s1Predicate.isAnon() && s2Predicate.isAnon()) {
      compareResult = -1;
    } else if (s1Predicate.isAnon() && !s2Predicate.isAnon()) {
      compareResult = 1;
    } else {
      if (s1Predicate.isAnon()) {
        s1Label = s1Predicate.getId().getLabelString();
        s2Label = s2Predicate.getId().getLabelString();
      } else {
        s1Label = s1Predicate.getLocalName() + " " + s1Predicate.getURI();
        s2Label = s2Predicate.getLocalName() + " " + s2Predicate.getURI();
      }
      compareResult = s1Label.compareTo(s2Label);
    }

    return compareResult;
  }

}

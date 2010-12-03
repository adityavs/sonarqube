/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2009 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.core.timemachine;

import org.junit.Test;
import org.sonar.api.database.model.Snapshot;
import org.sonar.jpa.test.AbstractDbUnitTestCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PastSnapshotFinderByDateTest extends AbstractDbUnitTestCase {
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @Test
  public void shouldFindDate() throws ParseException {
    setupData("shared");

    Snapshot projectSnapshot = getSession().getSingleResult(Snapshot.class, "id", 1010);
    PastSnapshotFinderByDate finder = new PastSnapshotFinderByDate(projectSnapshot, getSession());

    Date date = DATE_FORMAT.parse("2008-11-22");

    Snapshot snapshot = finder.findByDate(date);
    assertThat(snapshot.getId(), is(1006));
  }

  @Test
  public void shouldFindNearestLaterDate() throws ParseException {
    setupData("shared");

    Snapshot projectSnapshot = getSession().getSingleResult(Snapshot.class, "id", 1010);
    PastSnapshotFinderByDate finder = new PastSnapshotFinderByDate(projectSnapshot, getSession());

    Date date = DATE_FORMAT.parse("2008-11-24");
   
    Snapshot snapshot = finder.findByDate(date);
    assertThat(snapshot.getId(), is(1009));
  }
}

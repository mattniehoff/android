package org.nypl.simplified.books.covers

import org.nypl.simplified.books.core.FeedEntryOPDS

/**
 * A function to look up badge images for cover.
 */

interface BookCoverBadgeLookupType {

  /**
   * @return The badge image to use, if any, for the given book
   */

  fun badgeForEntry(entry: FeedEntryOPDS): BookCoverBadge?

}

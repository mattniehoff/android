package org.nypl.simplified.app;

import java.io.File;

import org.nypl.simplified.books.core.BookID;
import org.nypl.simplified.books.core.BookSnapshot;
import org.nypl.simplified.books.core.BooksType;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.io7m.jfunctional.OptionType;
import com.io7m.jfunctional.Some;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

public final class CatalogBookRead implements OnClickListener
{
  private final Activity activity;
  private final BookID   id;

  public CatalogBookRead(
    final Activity in_activity,
    final BookID in_id)
  {
    this.activity = NullCheck.notNull(in_activity);
    this.id = NullCheck.notNull(in_id);
  }

  @Override public void onClick(
    final @Nullable View v)
  {
    final SimplifiedAppServicesType app = Simplified.getAppServices();
    final BooksType books = app.getBooks();
    final Activity a = this.activity;

    final OptionType<BookSnapshot> snap_opt = books.booksSnapshotGet(this.id);

    if (snap_opt.isSome()) {
      final Some<BookSnapshot> some_snap = (Some<BookSnapshot>) snap_opt;
      final BookSnapshot snap = some_snap.get();
      final OptionType<File> book_opt = snap.getBook();
      if (book_opt.isSome()) {
        final Some<File> some_book = (Some<File>) book_opt;
        final File book = some_book.get();
        ReaderActivity.startActivity(a, book);
      } else {
        ErrorDialog
          .showError(
            a,
            "Bug: book claimed to be downloaded but no book file exists in storage",
            null);
      }
    } else {
      ErrorDialog.showError(a, "Book no longer exists!", null);
    }
  }
}
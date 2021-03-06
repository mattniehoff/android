package org.nypl.simplified.books.core;

/**
 * An exception indicating that book borrowing failed because the DRM client failed
 * when executing its workflow.
 */

public final class AccountNotReadyException extends BookBorrowException
{
  /**
   * Construct an exception.
   *
   * @param error_code The error code
   */

  public AccountNotReadyException(final String error_code)
  {
    super(error_code);
  }
}

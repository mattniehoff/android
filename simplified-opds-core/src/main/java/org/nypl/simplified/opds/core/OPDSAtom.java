package org.nypl.simplified.opds.core;

import com.io7m.jfunctional.OptionType;
import com.io7m.jfunctional.PartialFunctionType;
import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;
import org.nypl.simplified.rfc3339.core.RFC3339Formatter;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;

final class OPDSAtom implements Serializable
{
  private static final long serialVersionUID = 1L;

  private OPDSAtom()
  {
    throw new UnreachableCodeException();
  }

  static String findID(
    final Element ee)
    throws OPDSParseException
  {
    return OPDSXML.getFirstChildElementTextWithName(
      ee, OPDSFeedConstants.ATOM_URI, "id");
  }

  static OptionType<Calendar> findPublished(
    final Element e)
    throws DOMException, ParseException
  {
    final OptionType<Element> e_opt =
      OPDSXML.getFirstChildElementWithNameOptional(
        e, OPDSFeedConstants.ATOM_URI, "published");

    return e_opt.mapPartial(
      new PartialFunctionType<Element, Calendar, ParseException>()
      {
        @Override public Calendar call(
          final Element er)
          throws ParseException
        {
          final String text = er.getTextContent();
          final String trimmed = text.trim();
          return RFC3339Formatter.parseRFC3339Date(
            NullCheck.notNull(trimmed));
        }
      });
  }

  static String findTitle(
    final Element e)
    throws OPDSParseException
  {
    return OPDSXML.getFirstChildElementTextWithName(
      e, OPDSFeedConstants.ATOM_URI, "title");
  }

  static Calendar findUpdated(
    final Element e)
    throws OPDSParseException, ParseException
  {
    final String e_updated_raw = OPDSXML.getFirstChildElementTextWithName(
      e, OPDSFeedConstants.ATOM_URI, "updated");
    return RFC3339Formatter.parseRFC3339Date(e_updated_raw);
  }
}

import org.owasp.html.HtmlPolicyBuilder

object Sanitizer {
  def apply(untrustedHTML: String): String = {
    val policy = new HtmlPolicyBuilder()
      .allowElements("a")
      .allowUrlProtocols("https")
      .allowAttributes("href").onElements("a")
      .requireRelNofollowOnLinks()
      .toFactory();

    return policy.sanitize(untrustedHTML);
  }
}

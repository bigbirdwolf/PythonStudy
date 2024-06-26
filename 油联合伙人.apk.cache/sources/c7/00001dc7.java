package org.jsoup.safety;

import com.alipay.sdk.cons.b;
import com.umeng.commonsdk.proguard.e;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

/* loaded from: classes.dex */
public class Whitelist {
    private Set<TagName> tagNames = new HashSet();
    private Map<TagName, Set<AttributeKey>> attributes = new HashMap();
    private Map<TagName, Map<AttributeKey, AttributeValue>> enforcedAttributes = new HashMap();
    private Map<TagName, Map<AttributeKey, Set<Protocol>>> protocols = new HashMap();
    private boolean preserveRelativeLinks = false;

    public static Whitelist none() {
        return new Whitelist();
    }

    public static Whitelist simpleText() {
        return new Whitelist().addTags("b", "em", e.aq, "strong", "u");
    }

    public static Whitelist basic() {
        return new Whitelist().addTags(e.al, "b", "blockquote", "br", "cite", "code", "dd", "dl", SocializeProtocolConstants.PROTOCOL_KEY_DT, "em", e.aq, "li", "ol", e.ao, "pre", "q", "small", "span", "strike", "strong", "sub", "sup", "u", "ul").addAttributes(e.al, "href").addAttributes("blockquote", "cite").addAttributes("q", "cite").addProtocols(e.al, "href", "ftp", "http", b.a, "mailto").addProtocols("blockquote", "cite", "http", b.a).addProtocols("cite", "cite", "http", b.a).addEnforcedAttribute(e.al, "rel", "nofollow");
    }

    public static Whitelist basicWithImages() {
        return basic().addTags("img").addAttributes("img", "align", "alt", SocializeProtocolConstants.HEIGHT, "src", "title", SocializeProtocolConstants.WIDTH).addProtocols("img", "src", "http", b.a);
    }

    public static Whitelist relaxed() {
        return new Whitelist().addTags(e.al, "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd", "div", "dl", SocializeProtocolConstants.PROTOCOL_KEY_DT, "em", "h1", "h2", "h3", "h4", "h5", "h6", e.aq, "img", "li", "ol", e.ao, "pre", "q", "small", "span", "strike", "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u", "ul").addAttributes(e.al, "href", "title").addAttributes("blockquote", "cite").addAttributes("col", "span", SocializeProtocolConstants.WIDTH).addAttributes("colgroup", "span", SocializeProtocolConstants.WIDTH).addAttributes("img", "align", "alt", SocializeProtocolConstants.HEIGHT, "src", "title", SocializeProtocolConstants.WIDTH).addAttributes("ol", "start", "type").addAttributes("q", "cite").addAttributes("table", SocializeProtocolConstants.SUMMARY, SocializeProtocolConstants.WIDTH).addAttributes("td", "abbr", "axis", "colspan", "rowspan", SocializeProtocolConstants.WIDTH).addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", SocializeProtocolConstants.WIDTH).addAttributes("ul", "type").addProtocols(e.al, "href", "ftp", "http", b.a, "mailto").addProtocols("blockquote", "cite", "http", b.a).addProtocols("cite", "cite", "http", b.a).addProtocols("img", "src", "http", b.a).addProtocols("q", "cite", "http", b.a);
    }

    public Whitelist addTags(String... strArr) {
        Validate.notNull(strArr);
        for (String str : strArr) {
            Validate.notEmpty(str);
            this.tagNames.add(TagName.valueOf(str));
        }
        return this;
    }

    public Whitelist removeTags(String... strArr) {
        Validate.notNull(strArr);
        for (String str : strArr) {
            Validate.notEmpty(str);
            TagName valueOf = TagName.valueOf(str);
            if (this.tagNames.remove(valueOf)) {
                this.attributes.remove(valueOf);
                this.enforcedAttributes.remove(valueOf);
                this.protocols.remove(valueOf);
            }
        }
        return this;
    }

    public Whitelist addAttributes(String str, String... strArr) {
        Validate.notEmpty(str);
        Validate.notNull(strArr);
        Validate.isTrue(strArr.length > 0, "No attributes supplied.");
        TagName valueOf = TagName.valueOf(str);
        if (!this.tagNames.contains(valueOf)) {
            this.tagNames.add(valueOf);
        }
        HashSet hashSet = new HashSet();
        for (String str2 : strArr) {
            Validate.notEmpty(str2);
            hashSet.add(AttributeKey.valueOf(str2));
        }
        if (this.attributes.containsKey(valueOf)) {
            this.attributes.get(valueOf).addAll(hashSet);
        } else {
            this.attributes.put(valueOf, hashSet);
        }
        return this;
    }

    public Whitelist removeAttributes(String str, String... strArr) {
        Validate.notEmpty(str);
        Validate.notNull(strArr);
        Validate.isTrue(strArr.length > 0, "No attributes supplied.");
        TagName valueOf = TagName.valueOf(str);
        HashSet hashSet = new HashSet();
        for (String str2 : strArr) {
            Validate.notEmpty(str2);
            hashSet.add(AttributeKey.valueOf(str2));
        }
        if (this.tagNames.contains(valueOf) && this.attributes.containsKey(valueOf)) {
            Set<AttributeKey> set = this.attributes.get(valueOf);
            set.removeAll(hashSet);
            if (set.isEmpty()) {
                this.attributes.remove(valueOf);
            }
        }
        if (str.equals(":all")) {
            for (TagName tagName : this.attributes.keySet()) {
                Set<AttributeKey> set2 = this.attributes.get(tagName);
                set2.removeAll(hashSet);
                if (set2.isEmpty()) {
                    this.attributes.remove(tagName);
                }
            }
        }
        return this;
    }

    public Whitelist addEnforcedAttribute(String str, String str2, String str3) {
        Validate.notEmpty(str);
        Validate.notEmpty(str2);
        Validate.notEmpty(str3);
        TagName valueOf = TagName.valueOf(str);
        if (!this.tagNames.contains(valueOf)) {
            this.tagNames.add(valueOf);
        }
        AttributeKey valueOf2 = AttributeKey.valueOf(str2);
        AttributeValue valueOf3 = AttributeValue.valueOf(str3);
        if (this.enforcedAttributes.containsKey(valueOf)) {
            this.enforcedAttributes.get(valueOf).put(valueOf2, valueOf3);
        } else {
            HashMap hashMap = new HashMap();
            hashMap.put(valueOf2, valueOf3);
            this.enforcedAttributes.put(valueOf, hashMap);
        }
        return this;
    }

    public Whitelist removeEnforcedAttribute(String str, String str2) {
        Validate.notEmpty(str);
        Validate.notEmpty(str2);
        TagName valueOf = TagName.valueOf(str);
        if (this.tagNames.contains(valueOf) && this.enforcedAttributes.containsKey(valueOf)) {
            AttributeKey valueOf2 = AttributeKey.valueOf(str2);
            Map<AttributeKey, AttributeValue> map = this.enforcedAttributes.get(valueOf);
            map.remove(valueOf2);
            if (map.isEmpty()) {
                this.enforcedAttributes.remove(valueOf);
            }
        }
        return this;
    }

    public Whitelist preserveRelativeLinks(boolean z) {
        this.preserveRelativeLinks = z;
        return this;
    }

    public Whitelist addProtocols(String str, String str2, String... strArr) {
        Map<AttributeKey, Set<Protocol>> map;
        Set<Protocol> set;
        Validate.notEmpty(str);
        Validate.notEmpty(str2);
        Validate.notNull(strArr);
        TagName valueOf = TagName.valueOf(str);
        AttributeKey valueOf2 = AttributeKey.valueOf(str2);
        if (this.protocols.containsKey(valueOf)) {
            map = this.protocols.get(valueOf);
        } else {
            HashMap hashMap = new HashMap();
            this.protocols.put(valueOf, hashMap);
            map = hashMap;
        }
        if (map.containsKey(valueOf2)) {
            set = map.get(valueOf2);
        } else {
            HashSet hashSet = new HashSet();
            map.put(valueOf2, hashSet);
            set = hashSet;
        }
        for (String str3 : strArr) {
            Validate.notEmpty(str3);
            set.add(Protocol.valueOf(str3));
        }
        return this;
    }

    public Whitelist removeProtocols(String str, String str2, String... strArr) {
        Validate.notEmpty(str);
        Validate.notEmpty(str2);
        Validate.notNull(strArr);
        TagName valueOf = TagName.valueOf(str);
        AttributeKey valueOf2 = AttributeKey.valueOf(str2);
        if (this.protocols.containsKey(valueOf)) {
            Map<AttributeKey, Set<Protocol>> map = this.protocols.get(valueOf);
            if (map.containsKey(valueOf2)) {
                Set<Protocol> set = map.get(valueOf2);
                for (String str3 : strArr) {
                    Validate.notEmpty(str3);
                    set.remove(Protocol.valueOf(str3));
                }
                if (set.isEmpty()) {
                    map.remove(valueOf2);
                    if (map.isEmpty()) {
                        this.protocols.remove(valueOf);
                    }
                }
            }
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSafeTag(String str) {
        return this.tagNames.contains(TagName.valueOf(str));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSafeAttribute(String str, Element element, Attribute attribute) {
        TagName valueOf = TagName.valueOf(str);
        AttributeKey valueOf2 = AttributeKey.valueOf(attribute.getKey());
        if (!this.attributes.containsKey(valueOf) || !this.attributes.get(valueOf).contains(valueOf2)) {
            return !str.equals(":all") && isSafeAttribute(":all", element, attribute);
        } else if (this.protocols.containsKey(valueOf)) {
            Map<AttributeKey, Set<Protocol>> map = this.protocols.get(valueOf);
            return !map.containsKey(valueOf2) || testValidProtocol(element, attribute, map.get(valueOf2));
        } else {
            return true;
        }
    }

    private boolean testValidProtocol(Element element, Attribute attribute, Set<Protocol> set) {
        String absUrl = element.absUrl(attribute.getKey());
        if (absUrl.length() == 0) {
            absUrl = attribute.getValue();
        }
        if (!this.preserveRelativeLinks) {
            attribute.setValue(absUrl);
        }
        for (Protocol protocol : set) {
            String protocol2 = protocol.toString();
            if (protocol2.equals("#")) {
                if (isValidAnchor(absUrl)) {
                    return true;
                }
            } else {
                if (absUrl.toLowerCase().startsWith(protocol2 + ":")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidAnchor(String str) {
        return str.startsWith("#") && !str.matches(".*\\s.*");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Attributes getEnforcedAttributes(String str) {
        Attributes attributes = new Attributes();
        TagName valueOf = TagName.valueOf(str);
        if (this.enforcedAttributes.containsKey(valueOf)) {
            for (Map.Entry<AttributeKey, AttributeValue> entry : this.enforcedAttributes.get(valueOf).entrySet()) {
                attributes.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return attributes;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class TagName extends TypedValue {
        TagName(String str) {
            super(str);
        }

        static TagName valueOf(String str) {
            return new TagName(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class AttributeKey extends TypedValue {
        AttributeKey(String str) {
            super(str);
        }

        static AttributeKey valueOf(String str) {
            return new AttributeKey(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class AttributeValue extends TypedValue {
        AttributeValue(String str) {
            super(str);
        }

        static AttributeValue valueOf(String str) {
            return new AttributeValue(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class Protocol extends TypedValue {
        Protocol(String str) {
            super(str);
        }

        static Protocol valueOf(String str) {
            return new Protocol(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static abstract class TypedValue {
        private String value;

        TypedValue(String str) {
            Validate.notNull(str);
            this.value = str;
        }

        public int hashCode() {
            return 31 + (this.value == null ? 0 : this.value.hashCode());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                TypedValue typedValue = (TypedValue) obj;
                if (this.value == null) {
                    if (typedValue.value != null) {
                        return false;
                    }
                } else if (!this.value.equals(typedValue.value)) {
                    return false;
                }
                return true;
            }
            return false;
        }

        public String toString() {
            return this.value;
        }
    }
}
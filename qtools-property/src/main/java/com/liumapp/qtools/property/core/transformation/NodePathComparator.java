package com.liumapp.qtools.property.core.transformation;

import java.util.Comparator;
import static com.liumapp.qtools.property.core.transformation.ConfigurationTransformation.*;

/**
 * file NodePathComparator.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 */
class NodePathComparator implements Comparator<Object[]> {

    @Override
    public int compare(Object[] a, Object[] b) {
        for (int i = 0; i < Math.min(a.length, b.length); ++i) {
            if (a[i] == WILDCARD_OBJECT || b[i] == WILDCARD_OBJECT) {
                if (a[i] != WILDCARD_OBJECT || b[i] != WILDCARD_OBJECT) {
                    return a[i] == WILDCARD_OBJECT ? 1 : -1;
                }

            } else if (a[i] instanceof Comparable) {
                @SuppressWarnings("unchecked")
                final int comp = ((Comparable) a[i]).compareTo(b[i]);
                switch (comp) {
                    case 0:
                        break;
                    default:
                        return comp;
                }
            } else {
                return a[i].equals(b[i]) ? 0 : Integer.compare(a[i].hashCode(), b[i].hashCode());
            }
        }

        return Integer.compare(b.length, a.length);
    }
}

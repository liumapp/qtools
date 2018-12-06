package com.liumapp.qtools.property.core.transformation;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * file SingleConfigurationTransformation.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Base implementation of {@link ConfigurationTransformation}.
 *
 * <p>Transformations are executed from deepest in the configuration hierarchy outwards.</p>
 */

 */
class SingleConfigurationTransformation extends ConfigurationTransformation {
    private final MoveStrategy strategy;
    private final Map<Object[], TransformAction> actions;

    /**
     * Thread local {@link ConfigurationTransformation.NodePath} instance - used so we don't have to create lots of NodePath
     * instances.
     *
     * As such, data within paths is only guaranteed to be the same during a run of
     * a transform function.
     */
    private final ThreadLocal<ConfigurationTransformation.NodePath> sharedPath = ThreadLocal.withInitial(ConfigurationTransformation.NodePath::new);

    SingleConfigurationTransformation(Map<Object[], TransformAction> actions, MoveStrategy strategy) {
        this.actions = actions;
        this.strategy = strategy;
    }

    @Override
    public void apply(@NonNull ConfigurationNode node) {
        for (Map.Entry<Object[], TransformAction> ent : actions.entrySet()) {
            applySingleAction(node, ent.getKey(), 0, node, ent.getValue());
        }
    }

    private void applySingleAction(ConfigurationNode start, Object[] path, int startIdx, ConfigurationNode node, TransformAction action) {
        for (int i = startIdx; i < path.length; ++i) {
            if (path[i] == WILDCARD_OBJECT) {
                if (node.hasListChildren()) {
                    List<? extends ConfigurationNode> children = node.getChildrenList();
                    for (int di = 0; di < children.size(); ++di) {
                        path[i] = di;
                        applySingleAction(start, path, i + 1, children.get(di), action);
                    }
                    path[i] = WILDCARD_OBJECT;
                } else if (node.hasMapChildren()) {
                    for (Map.Entry<Object, ? extends ConfigurationNode> ent : node.getChildrenMap().entrySet()) {
                        path[i] = ent.getKey();
                        applySingleAction(start, path, i + 1, ent.getValue(), action);
                    }
                    path[i] = WILDCARD_OBJECT;
                } else {
                    // No children
                    return;
                }
                return;
            } else {
                node = node.getNode(path[i]);
                if (node.isVirtual()) {
                    return;
                }
            }
        }

        // apply action
        ConfigurationTransformation.NodePath nodePath = sharedPath.get();
        nodePath.arr = path;

        Object[] transformedPath = action.visitPath(nodePath, node);
        if (transformedPath != null && !Arrays.equals(path, transformedPath)) {
            this.strategy.move(node, start.getNode(transformedPath));
            node.setValue(null);
        }
    }
}

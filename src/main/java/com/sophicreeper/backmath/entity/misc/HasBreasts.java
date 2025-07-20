package com.sophicreeper.backmath.entity.misc;

import com.sophicreeper.backmath.misc.BMBreastPhysics;

/// Interface containing methods for rendering breasts on Back Math mobs (mainly termians and alcalytes).
public interface HasBreasts {
    /// Gets the breast size for an entity. Should be a value between <code>0</code> and <code>1</code>.
    float getBustSize();

    /// Sets the breast size for an entity. Values below <code>0.02</code> are not rendered.
    /// @param bustSize The breast size for an entity, ideally between <code>0</code> and <code>1</code>.
    void setBustSize(float bustSize);

    /// Gets the {@link BMBreastPhysics breast physics} instance for this entity, used to render their breasts.
    BMBreastPhysics getBreastPhysics();
}

/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemState;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.expandable.annotation.ExpandableItemStateFlags;

public abstract class AbstractExpandableItemViewHolder extends RecyclerView.ViewHolder implements ExpandableItemViewHolder {
    private final ExpandableItemState mExpandState = new ExpandableItemState();

    public AbstractExpandableItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExpandStateFlags(@ExpandableItemStateFlags int flags) {
        mExpandState.setFlags(flags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @ExpandableItemStateFlags
    public int getExpandStateFlags() {
        return mExpandState.getFlags();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NonNull
    public ExpandableItemState getExpandState() {
        return mExpandState;
    }
}

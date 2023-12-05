// Copyright (c) 2014-2015 Cago, Inc. All rights reserved.
// CAGO CONFIDENTIAL PROPRIETARY.
package com.spec.asms.common.utils;

public abstract class OnDialogActionListener {
	public abstract void onClick(int id, int secondId);
    public abstract void onPositiveClick();
    public abstract void onNegativeClick();
    public abstract void onOptionClick(int optionID);
    public abstract void onNeutralClick();
}

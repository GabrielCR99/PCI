package com.studio.pci.providers;

import com.studio.pci.models.Meeting;

public class MeetingDAO extends AbstractDAO<Meeting> {
    public MeetingDAO() {
        super("meetings");
    }
}

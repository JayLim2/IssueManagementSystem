package org.sergei.komarov.controllers.handbooks;

import java.util.Map;

public interface HandbookController {

    //add
    Map<String, Object> handleAddRequest(String name);

    //edit
    Map<String, Object> handleEditRequest(int id, String name);

    //delete
    Map<String, Object> handleDeleteRequest(int id);
}

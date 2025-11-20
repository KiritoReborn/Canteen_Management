package com.canteen.canteen_system.controller;

import com.canteen.canteen_system.model.MenuItem;
import com.canteen.canteen_system.repository.MenuRepository;
import com.canteen.canteen_system.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
public class MenuController {
    private final MenuRepository menuRepository;

    @GetMapping("/menus")
    public List<MenuItem> getMenu() {
        return menuRepository.findAll();
    }

    @GetMapping("/menu/{itemname}")
    public ResponseEntity<MenuItem> getMenuItemByName(@PathVariable String itemname) {
        var item=menuRepository.findByItemname(itemname);
        if(item==null){
            return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.ok(item);
    }









}


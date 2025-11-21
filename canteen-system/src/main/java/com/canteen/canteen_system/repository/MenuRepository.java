package com.canteen.canteen_system.repository;

import com.canteen.canteen_system.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuItem,Long> {
    MenuItem findByItemname(String itemName);

}

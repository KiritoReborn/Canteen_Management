package com.canteen.canteen_system.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class MenuItem {
    
    private Long id;
    private String itemname;
    private String description;
    private double price;
    private String category;

    
}
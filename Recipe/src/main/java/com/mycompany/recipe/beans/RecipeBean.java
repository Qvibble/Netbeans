/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.recipe.beans;

import com.mycompany.recipe.ConnectionFactory;
import com.mycompany.recipe.entities.Ingredient;
import com.mycompany.recipe.entities.Recipe;
import com.mycompany.recipe.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author jespe
 */
@Stateless
public class RecipeBean {
    public List<Recipe> getNewRecipes(){
        List<Recipe> recipes = new ArrayList<>();
        
        /* Hämtar 10 senast tillagda recept */
        try(Connection con = ConnectionFactory.getConnection()){
            String sql = "SELECT * FROM (SELECT * FROM recipes ORDER BY id DESC LIMIT 10) var ORDER BY id ASC";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            ResultSet recipeData = prepStmt.executeQuery();

            while(recipeData.next()){
                int id = recipeData.getInt("id");
                String name = recipeData.getString("name");
                String description = recipeData.getString("description");
                String steps = recipeData.getString("steps");
                String image = recipeData.getString("image");
                
                /* Hämtar alla likes receptet har */
                int likes = 0;

                sql = "SELECT * FROM likes WHERE recipe_id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, id);
                ResultSet likesData = prepStmt.executeQuery();
                while(likesData.next()){
                    likes++;
                }
                
                /* Hämtar användaren som skapat recepets id */
                sql = "SELECT * FROM user_recipes WHERE recipe_id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, id);
                ResultSet userRecipeData = prepStmt.executeQuery();
                userRecipeData.next();                
                
                /* Hämtar användarens namn */
                sql = "SELECT * FROM users WHERE id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, userRecipeData.getInt("user_id"));
                ResultSet userData = prepStmt.executeQuery();
                userData.next();
                String username = userData.getString("username");
                
                sql = "SELECT * FROM recipe_ingredients WHERE recipe_id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, id);
                ResultSet recIngData = prepStmt.executeQuery();
                
                List<Ingredient> ingredients = new ArrayList<>();
                while(recIngData.next()){
                    int ingredientId = recIngData.getInt("ingredientId");
                    String amount = recIngData.getString("amount");
                    
                    sql = "SELECT * FROM ingredients WHERE id = ?";
                    prepStmt = con.prepareStatement(sql);
                    prepStmt.setInt(1, ingredientId);
                    ResultSet ingredientData = prepStmt.executeQuery();
                    ingredientData.next();
                    
                    String ingredientName = ingredientData.getString("name");
                    
                    ingredients.add(new Ingredient(ingredientName, amount));
                }
                
                recipes.add(new Recipe(username, name, description, steps, ingredients, image, likes));
            }
            
            return recipes;
        }catch(Exception e){
            System.out.println("Error: RecipeBean, getNewRecipes: " + e);
            return null;
        }
    }

    public List<Recipe> getRandomRecipes(){
        List<Recipe> recipes = new ArrayList<>();
        
        /* Hämtar 10 slumpade recept */
        try(Connection con = ConnectionFactory.getConnection()){
            String sql = "SELECT * FROM recipes ORDER BY RAND() LIMIT 10";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            ResultSet recipeData = prepStmt.executeQuery();

            while(recipeData.next()){
                int id = recipeData.getInt("id");
                String name = recipeData.getString("name");
                String description = recipeData.getString("description");
                String steps = recipeData.getString("steps");
                String image = recipeData.getString("image");
                
                /* Hämtar alla likes receptet har */
                int likes = 0;

                sql = "SELECT * FROM likes WHERE recipe_id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, id);
                ResultSet likesData = prepStmt.executeQuery();
                while(likesData.next()){
                    likes++;
                }

                /* Hämtar användaren som skapat recepets id */
                sql = "SELECT * FROM user_recipes WHERE recipe_id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, id);
                ResultSet userRecipeData = prepStmt.executeQuery();
                userRecipeData.next();                

                /* Hämtar användarens namn */
                sql = "SELECT * FROM users WHERE id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, userRecipeData.getInt("user_id"));
                ResultSet userData = prepStmt.executeQuery();
                userData.next();
                String username = userData.getString("username");

                sql = "SELECT * FROM recipe_ingredients WHERE recipe_id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, id);
                ResultSet recIngData = prepStmt.executeQuery();

                List<Ingredient> ingredients = new ArrayList<>();
                while(recIngData.next()){
                    int ingredientId = recIngData.getInt("ingredient_id");
                    String amount = recIngData.getString("amount");
                    
                    sql = "SELECT * FROM ingredients WHERE id = ?";
                    prepStmt = con.prepareStatement(sql);
                    prepStmt.setInt(1, ingredientId);
                    ResultSet ingredientData = prepStmt.executeQuery();
                    ingredientData.next();

                    String ingredientName = ingredientData.getString("name");
                    
                    ingredients.add(new Ingredient(ingredientName, amount));
                }
                
                recipes.add(new Recipe(id, username, name, description, steps, ingredients, image, likes));
            }
            
            return recipes;
        }catch(Exception e){
            System.out.println("Error: RecipeBean, getRandomRecipes: " + e);
            return null;
        }
    }
    
    public List<Recipe> getLikedRecipes(String username){
        List<Recipe> recipes = new ArrayList<>();
        
        /* Hämtar användarens gillade recept */
        try(Connection con = ConnectionFactory.getConnection()){
            /* Hämtar användarens id med hjälp av användarnamnet */
            String sql = "SELECT id FROM users WHERE username = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, username);
            ResultSet userData = prepStmt.executeQuery();     
            userData.next();
            
            /* Hämtar recept id */
            sql = "SELECT recipe_id FROM likes WHERE user_id = ?";
            prepStmt = con.prepareStatement(sql);
            prepStmt.setInt(1, userData.getInt("id"));
            ResultSet likedData = prepStmt.executeQuery();
            
            /* Hämtar alla gillade recept */
            while(likedData.next()){
                sql = "SELECT * FROM recipes WHERE id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, likedData.getInt("id"));
                ResultSet recipeData = prepStmt.executeQuery();

                while(recipeData.next()){
                    int id = recipeData.getInt("id");
                    String name = recipeData.getString("name");
                    String description = recipeData.getString("description");
                    String steps = recipeData.getString("steps");
                    String image = recipeData.getString("image");

                    /* Hämtar alla likes receptet har */
                    int likes = 0;

                    sql = "SELECT * FROM likes WHERE recipe_id = ?";
                    prepStmt = con.prepareStatement(sql);
                    prepStmt.setInt(1, id);
                    ResultSet likesData = prepStmt.executeQuery();
                    while(likesData.next()){
                        likes++;
                    }
                    
                    sql = "SELECT * FROM recipe_ingredients WHERE recipe_id = ?";
                    prepStmt = con.prepareStatement(sql);
                    prepStmt.setInt(1, id);
                    ResultSet recIngData = prepStmt.executeQuery();

                    List<Ingredient> ingredients = new ArrayList<>();
                    while(recIngData.next()){
                        int ingredientId = recIngData.getInt("ingredientId");
                        String amount = recIngData.getString("amount");

                        sql = "SELECT * FROM ingredients WHERE id = ?";
                        prepStmt = con.prepareStatement(sql);
                        prepStmt.setInt(1, ingredientId);
                        ResultSet ingredientData = prepStmt.executeQuery();
                        ingredientData.next();

                        String ingredientName = ingredientData.getString("name");

                        ingredients.add(new Ingredient(ingredientName, amount));
                    }

                    recipes.add(new Recipe(id, username, name, description, steps, ingredients, image, likes));
                }                
            }

            return recipes;
        }catch(Exception e){
            System.out.println("Error: RecipeBean, getLikedRecipes: " + e);
            return null;
        }
    }
    
    public List<Recipe> getUserRecipes(String username){
        List<Recipe> recipes = new ArrayList<>();
        
        /* Hämtar användarens skapade recept */
        try(Connection con = ConnectionFactory.getConnection()){
            /* Hämtar användarens id med hjälp av användarnamnet */
            String sql = "SELECT id FROM users WHERE username = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, username);
            ResultSet userData = prepStmt.executeQuery();     
            userData.next();
            
            /* Hämtar recept id */
            sql = "SELECT recipe_id FROM userRecipes WHERE user_id = ?";
            prepStmt = con.prepareStatement(sql);
            prepStmt.setInt(1, userData.getInt("id"));
            ResultSet recipeId = prepStmt.executeQuery();
            
            /* Hämtar alla recept som användaren skapat recept */
            while(recipeId.next()){
                sql = "SELECT * FROM recipes WHERE id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, recipeId.getInt("id"));
                ResultSet recipeData = prepStmt.executeQuery();

                while(recipeData.next()){
                    int id = recipeData.getInt("id");
                    String name = recipeData.getString("name");
                    String description = recipeData.getString("description");
                    String steps = recipeData.getString("steps");
                    String image = recipeData.getString("image");

                    /* Hämtar alla likes receptet har */
                    int likes = 0;

                    sql = "SELECT * FROM likes WHERE recipe_id = ?";
                    prepStmt = con.prepareStatement(sql);
                    prepStmt.setInt(1, id);
                    ResultSet likesData = prepStmt.executeQuery();
                    while(likesData.next()){
                        likes++;
                    }
                    
                    sql = "SELECT * FROM recipe_ingredients WHERE recipe_id = ?";
                    prepStmt = con.prepareStatement(sql);
                    prepStmt.setInt(1, id);
                    ResultSet recIngData = prepStmt.executeQuery();

                    List<Ingredient> ingredients = new ArrayList<>();
                    while(recIngData.next()){
                        int ingredientId = recIngData.getInt("ingredientId");
                        String amount = recIngData.getString("amount");

                        sql = "SELECT * FROM ingredients WHERE id = ?";
                        prepStmt = con.prepareStatement(sql);
                        prepStmt.setInt(1, ingredientId);
                        ResultSet ingredientData = prepStmt.executeQuery();
                        ingredientData.next();

                        String ingredientName = ingredientData.getString("name");

                        ingredients.add(new Ingredient(ingredientName, amount));
                    }

                    recipes.add(new Recipe(id, username, name, description, steps, ingredients, image, likes));
                }                
            }

            return recipes;
        }catch(Exception e){
            System.out.println("Error: RecipeBean, getUserRecipes: " + e);
            return null;
        }
    }
    
    public List<Recipe> getSearchedRecipes(String searchTerm){
        List<Recipe> recipes = new ArrayList<>();
        
        /* Hämtar alla recept som innehåller söktermen */
        try(Connection con = ConnectionFactory.getConnection()){
            String sql = "SELECT * FROM recipes WHERE name = %?%";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, searchTerm);
            ResultSet recipeData = prepStmt.executeQuery();

            while(recipeData.next()){
                int id = recipeData.getInt("id");
                String name = recipeData.getString("name");
                String description = recipeData.getString("description");
                String steps = recipeData.getString("steps");
                String image = recipeData.getString("image");
                
                /* Hämtar alla likes receptet har */
                int likes = 0;
                
                sql = "SELECT * FROM likes WHERE recipe_id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, id);
                ResultSet likesData = prepStmt.executeQuery();
                while(likesData.next()){
                    likes++;
                }
                
                /* Hämtar användaren som skapat recepets id */
                sql = "SELECT * FROM user_recipes WHERE recipe_id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, id);
                ResultSet userRecipeData = prepStmt.executeQuery();
                userRecipeData.next();                
                
                /* Hämtar användarens namn */
                sql = "SELECT * FROM users WHERE id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, userRecipeData.getInt("user_id"));
                ResultSet userData = prepStmt.executeQuery();
                userData.next();
                String username = userData.getString("username");
                
                sql = "SELECT * FROM recipe_ingredients WHERE recipe_id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, id);
                ResultSet recIngData = prepStmt.executeQuery();
                
                List<Ingredient> ingredients = new ArrayList<>();
                while(recIngData.next()){
                    int ingredientId = recIngData.getInt("ingredientId");
                    String amount = recIngData.getString("amount");
                    
                    sql = "SELECT * FROM ingredients WHERE id = ?";
                    prepStmt = con.prepareStatement(sql);
                    prepStmt.setInt(1, ingredientId);
                    ResultSet ingredientData = prepStmt.executeQuery();
                    ingredientData.next();
                    
                    String ingredientName = ingredientData.getString("name");
                    
                    ingredients.add(new Ingredient(ingredientName, amount));
                }
                
                recipes.add(new Recipe(id, username,name, description, steps, ingredients, image, likes));
            }
            
            return recipes;
        }catch(Exception e){
            System.out.println("Error: RecipeBean, getSearchedRecipes: " + e);
            return null;
        }
    }
    
    public List<Recipe> getRecipe(String recipeId){
        List<Recipe> recipes = new ArrayList<>();
        
        /* Hämtar ett recept med hjälp av id */
        try(Connection con = ConnectionFactory.getConnection()){
            String sql = "SELECT * FROM recipes WHERE id = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, recipeId);
            ResultSet recipeData = prepStmt.executeQuery();

            while(recipeData.next()){
                int id = recipeData.getInt("id");
                String name = recipeData.getString("name");
                String description = recipeData.getString("description");
                String steps = recipeData.getString("steps");
                String image = recipeData.getString("image");
                
                /* Hämtar alla likes receptet har */
                int likes = 0;
                
                sql = "SELECT * FROM likes WHERE recipe_id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, id);
                ResultSet likesData = prepStmt.executeQuery();
                while(likesData.next()){
                    likes++;
                }
                
                /* Hämtar användaren som skapat recepets id */
                sql = "SELECT * FROM user_recipes WHERE recipe_id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, id);
                ResultSet userRecipeData = prepStmt.executeQuery();
                userRecipeData.next();                
                
                /* Hämtar användarens namn */
                sql = "SELECT * FROM users WHERE id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, userRecipeData.getInt("user_id"));
                ResultSet userData = prepStmt.executeQuery();
                userData.next();
                String username = userData.getString("username");
                
                /* Hämtar ingrediensernas id från receptet */                
                sql = "SELECT * FROM recipe_ingredients WHERE recipe_id = ?";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, id);
                ResultSet recIngData = prepStmt.executeQuery();
                
                List<Ingredient> ingredients = new ArrayList<>();
                while(recIngData.next()){
                    int ingredientId = recIngData.getInt("ingredientId");
                    String amount = recIngData.getString("amount");
                    
                    sql = "SELECT * FROM ingredients WHERE id = ?";
                    prepStmt = con.prepareStatement(sql);
                    prepStmt.setInt(1, ingredientId);
                    ResultSet ingredientData = prepStmt.executeQuery();
                    ingredientData.next();
                    
                    String ingredientName = ingredientData.getString("name");
                    
                    ingredients.add(new Ingredient(ingredientName, amount));
                }
                
                recipes.add(new Recipe(id, username, name, description, steps, ingredients, image, likes));
            }
            
            return recipes;
        }catch(Exception e){
            System.out.println("Error: RecipeBean, getRecipe: " + e);
            return null;
        }
    }
    
    public int saveRecipe(Recipe recipe){
        try(Connection con = ConnectionFactory.getConnection()){
            String sql = "INSERT INTO recipes(name, description, steps, image) VALUES(?, ?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, recipe.getName());
            prepStmt.setString(2, recipe.getDescription());
            prepStmt.setString(3, recipe.getSteps());
            prepStmt.setString(4, recipe.getImage());

            prepStmt.executeUpdate();
            
            /* Lägg till ingredienser i databasen om de inte redan finns */            
            List<Ingredient> ingredients = recipe.getIngredients();
            for(Ingredient i : ingredients){
                sql = "INSERT INTO ingredients(name) SELECT * FROM (SELECT ?) AS temp\n" +
                      "WHERE NOT EXISTS (SELECT name FROM ingredients WHERE name = ?)";
                prepStmt = con.prepareStatement(sql);            
                prepStmt.setString(1, i.getName());
                prepStmt.setString(2, i.getName());                
                prepStmt.executeUpdate();
                
                /* Lägg till ingredienser för receptet */
                sql = "INSERT INTO recipe_ingredients(recipe_id, amount, ingredient_id) VALUES((SELECT id FROM recipes ORDER BY ID DESC LIMIT 1), ?, (SELECT id FROM ingredients WHERE name = ?))";
                prepStmt = con.prepareStatement(sql);
                prepStmt.setString(1, i.getAmount());
                prepStmt.setString(2, i.getName());
                prepStmt.executeUpdate();
            }
            
            sql = "INSERT INTO user_recipes(user_id, recipe_id) VALUES((SELECT id FROM users WHERE username = ?), (SELECT id FROM recipes ORDER BY ID DESC LIMIT 1))";
            prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, recipe.getUsername());            
            
            return prepStmt.executeUpdate();
        }catch(Exception e){
            System.out.println("Error: RecipeBean, saveRecipe");
            return 0;
        }
    }
    
    public int editRecipe(Recipe recipe){
        try(Connection con = ConnectionFactory.getConnection()){
            String sql = "UPDATE SET recipe(name, description, steps, image) VALUES(?, ?, ?, ?) WHERE id = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, recipe.getName());
            prepStmt.setString(2, recipe.getDescription());
            prepStmt.setString(3, recipe.getSteps());
            prepStmt.setString(4, recipe.getImage());
            prepStmt.setInt(5, recipe.getId());

            return prepStmt.executeUpdate();
        }catch(Exception e){
            System.out.println("Error: RecipeBean, editRecipe");
            return 0;
        }
    }
    
    public int removeRecipe(String recipeId){
        try(Connection con = ConnectionFactory.getConnection()){
            String sql = "DELETE FROM recipe WHERE id = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, recipeId);
            
            //Fler saker ska tas bort
            //Ta bort likes, vem som skapat recept etc

            return prepStmt.executeUpdate();
        }catch(Exception e){
            System.out.println("Error: RecipeBean, removeRecipe");
            return 0;
        }
    }
    
    
}

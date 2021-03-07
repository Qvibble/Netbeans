/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.recipe.resources;

import com.google.gson.Gson;
import com.mycompany.recipe.beans.RecipeBean;
import com.mycompany.recipe.entities.Recipe;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author jespe
 */
@Path("recipe")
public class RecipeResource {
    @EJB
    RecipeBean recipeBean;
    
    @GET
    @Path("new")
    public Response getNewRecipes(){
        /* Om det gick att hämta recept */
        if(recipeBean.getNewRecipes() != null){
            return Response.ok(recipeBean.getNewRecipes()).build();     
        /* Om det inte gick att hämta recept */
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("random")
    public Response getRandomRecipes(){
        /* Om det gick att hämta recept */
        if(recipeBean.getRandomRecipes() != null){
            return Response.ok(recipeBean.getRandomRecipes()).build(); 
        /* Om det inte gick att hämta recept */
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("liked")
    public Response getLikedRecipes(@HeaderParam("Username")String username){
        /* Om det gick att hämta recept */
        if(recipeBean.getLikedRecipes(username) != null){
            return Response.ok(recipeBean.getLikedRecipes(username)).build(); 
        /* Om det inte gick att hämta recept */
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("user")
    public Response getUserRecipes(@HeaderParam("Username")String username){
        /* Om det gick att hämta recept */
        if(recipeBean.getUserRecipes(username) != null){
            return Response.ok(recipeBean.getUserRecipes(username)).build(); 
        /* Om det inte gick att hämta recept */
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("search")
    public Response getSearchedRecipes(@HeaderParam("SearchTerm")String searchTerm){
        /* Om det gick att hämta recept */
        if(recipeBean.getSearchedRecipes(searchTerm) != null){
            return Response.ok(recipeBean.getSearchedRecipes(searchTerm)).build(); 
        /* Om det inte gick att hämta recept */
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("id")
    public Response getRecipe(@HeaderParam("RecipeId")String recipeId){
        /* Om det gick att hämta recept */
        if(recipeBean.getRecipe(recipeId) != null){
            return Response.ok(recipeBean.getRecipe(recipeId)).build(); 
        /* Om det inte gick att hämta recept */
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    /**
     * Sparar receptet i databasen och vem som skapat det
     * 
     * Tar emot användarnamn,{receptJSON}
     * 
     * @param recipeData
     * @return
     */
    @POST
    @Path("create")
    public Response createRecipe(String recipeData){     
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(recipeData, Recipe.class);

        /* Om det inte gick att spara */
        if(recipeBean.saveRecipe(recipe) == 0){
            return Response.status(Response.Status.BAD_REQUEST).build();
        /* Om det gick att spara */
        }else{
            return Response.status(Response.Status.CREATED).build();
        }        
    }
    
    /**
     * 
     * 
     * @param recipeData
     * @return
     */
    @PUT
    @Path("edit")
    public Response editRecipe(String recipeData){
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(recipeData, Recipe.class);
        
        /* Om det inte gick att ändra */
        if(recipeBean.editRecipe(recipe) == 0){
            return Response.status(Response.Status.BAD_REQUEST).build();
        /* Om det gick att spara */
        }else{
            return Response.status(Response.Status.CREATED).build();
        }  
    }
    
    @DELETE
    @Path("delete")
    public Response removeRecipe(@HeaderParam("RecipeId")String recipeId){  
        /* Om det inte gick att ändra */
        if(recipeBean.removeRecipe(recipeId) == 0){
            return Response.status(Response.Status.BAD_REQUEST).build();
        /* Om det gick att spara */
        }else{
            return Response.status(Response.Status.CREATED).build();
        }  
    }
}
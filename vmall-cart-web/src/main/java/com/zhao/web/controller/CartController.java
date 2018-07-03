package com.zhao.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhao.pojo.Item;
import com.zhao.service.CartService;
import com.zhao.service.ItemService;
import com.zhao.util.JsonObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-07-01 11:21
 * @描述
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Value("${CART_COOKIE_KEY}")
    private String CART_COOKIE_KEY;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CartService cartService;

    @RequestMapping("/add/{itemId}")
    public String addItem(@RequestParam("num") int num,@PathVariable("itemId") long itemId,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        List<Item> cartItem = getCartItem(request);
        JSONObject sessionUser = (JSONObject) request.getSession().getAttribute("user");
        //如果存在当前商品，则累加，不存在则重置
        boolean isExist = false;
        if(cartItem.size()>0){
            Item paramItem = new Item();
            paramItem.setId(itemId);
            if(cartItem.contains(paramItem)){
                isExist = true;
                Item cookieItem = cartItem.get(cartItem.indexOf(paramItem));
                cookieItem.setNum(cookieItem.getNum()+num);
            }
        }
        if(!isExist){
            //查询数据库，获取Item信息
            Item itemById = itemService.getItemById(itemId);
            itemById.setNum(num);
            String itemImg = itemById.getImage();
            if(null!=itemImg&&!itemImg.trim().equals("")){
                itemById.setImage(itemImg.split(",")[0]);
            }
            cartItem.add(itemById);
        }

        //todo:如果已登录,合并cookie中的商品
        if(null!=sessionUser){
            boolean isSucc = cartService.mergeCart(cartItem);

            //把cookie响应给客户端
            Cookie cookie = new Cookie(CART_COOKIE_KEY,null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return  "cartSuccess";
        }

        //把cookie响应给客户端
        Cookie cookie = new Cookie(CART_COOKIE_KEY, URLEncoder.encode(JSONObject.toJSONString(cartItem), "utf-8"));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7); //7天
        response.addCookie(cookie);
        return  "cartSuccess";
    }

    @RequestMapping("/list")
    public String itemList(HttpServletRequest request,Model model){
        JSONObject sessionUser = (JSONObject) request.getSession().getAttribute("user");
        List<Item> cartItem = null;
        //购物车列表，如果已经登录，则查询redis
        if(null!=sessionUser){
            cartItem  = cartService.getCart();
        }else{
            //否则查询cookie
            cartItem  = getCartItem(request);
        }
        model.addAttribute("cartList", cartItem);
        return  "cart";
    }

     /*
      *@描述  获取客户端保存的购物车信息
      *@创建时间 2018/7/1 11:54
      **/
    private List<Item> getCartItem(HttpServletRequest request){
        List<Item> items = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals(CART_COOKIE_KEY)){
                    try {
                        items  = JSONArray.parseArray(URLDecoder.decode(cookie.getValue(),"utf-8"),Item.class);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return  items;
    }
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public JsonObj updateCart(@PathVariable("itemId") long itemId,@PathVariable("num") int num,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject sessionUser = (JSONObject) request.getSession().getAttribute("user");
        if(null!=sessionUser){
            //更新redis中cart
            cartService.updateCartById(itemId,num);
            return JsonObj.success();
        }

        //拿到cookie
        List<Item> cartItem = getCartItem(request);
        if(cartItem.size()>0){
            Item paramItem = new Item();
            paramItem.setId(itemId);
            if(cartItem.contains(paramItem)){
                Item cookieItem = cartItem.get(cartItem.indexOf(paramItem));
                cookieItem.setNum(num);
            }
        }
        //把cookie响应给客户端
        Cookie cookie = new Cookie(CART_COOKIE_KEY, URLEncoder.encode(JSONObject.toJSONString(cartItem),"utf-8"));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7); //7天
        response.addCookie(cookie);
        return JsonObj.success();
    }

    @RequestMapping("/delete/{itemId}")
    public String deleteCart(@PathVariable("itemId") long itemId,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject sessionUser = (JSONObject) request.getSession().getAttribute("user");
        if(null!=sessionUser){
            //删除redis中cart
            cartService.deleteCartById(itemId);
            return "redirect:/cart/list";
        }

        //拿到cookie
        List<Item> cartItem = getCartItem(request);
        if(cartItem.size()>0){
            Item paramItem = new Item();
            paramItem.setId(itemId);
            if(cartItem.contains(paramItem)){
                cartItem.remove(paramItem);
            }
        }
        //把cookie响应给客户端
        Cookie cookie = new Cookie(CART_COOKIE_KEY,URLEncoder.encode(JSONObject.toJSONString(cartItem),"utf-8"));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7); //7天
        response.addCookie(cookie);
        return "redirect:/cart/list";
    }
}
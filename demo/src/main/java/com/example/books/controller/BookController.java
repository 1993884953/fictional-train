package com.example.books.controller;

import com.example.books.dto.BookRequest;
import com.example.books.dto.Pagination;
import com.example.books.entity.Book;
import com.example.books.mapper.BookMapper;
import com.example.books.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//解决跨域问题
@CrossOrigin
@RestController
public class BookController {
    //@Autowired //自动连接接口
    @Resource //自动连接接口
    BookMapper bookMapper;

    //复杂查询图书
    @GetMapping("/books")
    //返回泛型Map集合，传入书籍参数，分页信息
    public Result<Map<String, Object>> getAllBooks(Book book, Pagination pagination){
        //查询数量
        pagination.setTotal(bookMapper.count(book));
        //为0时报错
        if(pagination.getTotal()==0){
            return new Result<>("ERROR","未查寻到图书",null);
        }
        //获取所有信息
        List<Book> books = bookMapper.findBooks(book,pagination);

        //设置返回模板
        Result<Map<String, Object>> result;
        //设置返回参数data，并且添加数据
        Map<String, Object> data=new HashMap<>();
        data.put("pagination",pagination);
        data.put("books",books);

        result=new Result<>("SUCCESS","查找成功",data);
        return result;

    }

    //获取所有图书
//    @GetMapping("/books")
//    public Result<List <Book>> getResults(){
//        List<Book> books=bookMapper.findAllBook();
//        Result<List<Book>> result=new Result<>("SUCCESS",null,books);
//        return result;
//    }
    //获取单本图书
    @GetMapping("/books/{bookId}")
    public Result<Book> paramsGet(@PathVariable String bookId){
        Book book=bookMapper.findIdBook(bookId);
        Result<Book> result;

        if (book!=null){
            result=new Result<>("SUCCESS","查找成功",book);
        }else{
            result=new Result<>("ERROR","查无此书",null);
        }

        System.out.println(result);
        return result;
    }
    //新增图书
    @PostMapping("/books")
    public Result<Book> paramsPost(BookRequest bookRequest){
        //创建book类接收参数
        Book book=Book.builder().name(bookRequest.getName()).describe(bookRequest.getDescribe()).price(bookRequest.getPrice()).build();
        //创建返回结果
        Result<Book> result;
        //判断是否为空
        if(book.getName()==null||book.getDescribe()==null || book.getName().equals("") || book.getDescribe().equals("")){
            result=new Result<>("ERROR","描述和书名不能为空",null);
            return result;
        }
        //传入参数，插入mysql
        bookMapper.insertBook(book);
        if(book.getId()!=0){
            result= new Result<>("SUCCESS", "新增成功", book);
        }else {
            result=new Result<>("ERROR","新增失败",null);
        }
    return result;
    }
    //更改图书信息
    @PostMapping("/books/{bookId}")
    public Result<String> paramsUpdate(@PathVariable String bookId,@RequestBody BookRequest bookRequest){
        //创建book类接收参数
        Book book=Book.builder().name(bookRequest.getName()).describe(bookRequest.getDescribe()).price(bookRequest.getPrice()).build();
        //创建返回结果
        Result<String> result;
        //判断是否为空
        if(book.getName()==null||book.getDescribe()==null|| book.getName().equals("") || book.getDescribe().equals("")){
            result=new Result<>("ERROR","描述和书名不能为空",null);
            return result;
        }
        //传入参数，插入mysql
        int update=bookMapper.update(bookId,book);
        if(update==1){
            result=new Result<>("SUCCESS","更新成功",null);
        }else{
            result=new Result<>("ERROR","更新失败,未查到书籍信息",null);
        }
        return result;
    }
    //删除图书Delete
    @DeleteMapping("/books/{bookId}")
    public Result<String> paramsDelete(@PathVariable String bookId){
        //接收删除数量信息
        int delete=bookMapper.delete(bookId);
        //创建返回结果
        Result<String> result;
        //逻辑判断
        if (delete==1){
            result=new Result<>("SUCCESS","删除成功",null);
        }else{
            result=new Result<>("ERROR","删除失败,图书不存在",null);
        }
        return result;
    }

}

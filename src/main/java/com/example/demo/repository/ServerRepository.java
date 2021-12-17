package com.example.demo.repository;

import com.example.demo.model.Server;

import java.util.List;

public interface ServerRepository extends BaseRepository<Server, Long>{

    Server findServerByName(String name);


}

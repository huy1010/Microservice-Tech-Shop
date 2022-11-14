package com.techshop.productservice.service;

import com.techshop.productservice.entity.Tag;
import com.techshop.productservice.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagServiceImpl implements TagService{
    private TagRepository repository;

    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tag getTagById(Long tagId) {
        Optional<Tag> tag = repository.findById(tagId);

        if(!tag.isPresent())
            throw new IllegalStateException("Tag with id " + tagId + " is not exist");

        return tag.get();
    }
}

package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.dto.ReviewDTO;
import com.dev.yank.ecommerce.exception.ReviewNotFoundException;
import com.dev.yank.ecommerce.mapper.ReviewMapper;
import com.dev.yank.ecommerce.model.Review;
import com.dev.yank.ecommerce.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    public Page<ReviewDTO> getReviews(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAll(pageable);
        return reviews.map(reviewMapper::toDTO);
    }

    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + id));
        return reviewMapper.toDTO(review);
    }

    @Transactional
    public ReviewDTO createReview(ReviewDTO newReview) {
        Review review = reviewMapper.toEntity(newReview);
        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.toDTO(updatedReview);
    }

    @Transactional
    public ReviewDTO updateReview(Long id, ReviewDTO updateReview) {
        Review existingUser = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + id));

        existingUser.setComment(updateReview.comment());
        existingUser.setRating(updateReview.rating());

        Review updatedReview = reviewRepository.save(existingUser);
        return reviewMapper.toDTO(updatedReview);
    }

    @Transactional
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Review not found with ID: " + id);
        }
    }
}

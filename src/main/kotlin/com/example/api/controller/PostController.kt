package com.example.api.controller

import com.example.api.domain.Post
import com.example.api.iusecase.post.CreatePostInput
import com.example.api.iusecase.post.CreatePostInputData
import com.example.api.iusecase.post.DeletePostInput
import com.example.api.iusecase.post.GetPostsInput
import com.example.api.iusecase.post.ICreatePost
import com.example.api.iusecase.post.IDeletePost
import com.example.api.iusecase.post.IGetPosts
import com.example.api.iusecase.post.IUpdatePost
import com.example.api.iusecase.post.UpdatePostInput
import com.example.api.iusecase.post.UpdatePostInputData
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class GetPostsResponseBody(
	val posts: List<Post>,
)

data class CreatePostRequestBody(
	val title: String,
	val content: String,
)

data class CreatePostResponseBody(
	val post: Post,
)

data class UpdatePostRequestBody(
	val title: String,
	val content: String,
)

@RestController
class PostController(
	private val createPostU: ICreatePost,
	private val updatePostU: IUpdatePost,
	private val deletePostU: IDeletePost,
	private val getPostsU: IGetPosts,
) {
	@GetMapping("/posts")
	fun getAll(
		@CookieValue(COOKIE_NAME) token: String,
	): GetPostsResponseBody {
		val input = GetPostsInput(userId = token)
		val output = getPostsU.run(input)
		return GetPostsResponseBody(posts = output.posts)
	}

	@PostMapping("/posts")
	fun create(
		@CookieValue(COOKIE_NAME) token: String,
		@RequestBody body: CreatePostRequestBody,
	): CreatePostResponseBody {
		val inputData = CreatePostInputData(title = body.title, content = body.content)
		val input = CreatePostInput(token = token, data = inputData)
		val output = createPostU.run(input)
		return CreatePostResponseBody(post = output.post)
	}

	@PatchMapping("/posts/{postId}")
	fun update(
		@CookieValue(COOKIE_NAME) token: String,
		@PathVariable("postId") postId: String,
		@RequestBody body: UpdatePostRequestBody,
	) {
		val inputData = UpdatePostInputData(title = body.title, content = body.content)
		val input = UpdatePostInput(token = token, postId = postId, data = inputData)
		updatePostU.run(input)
	}

	@DeleteMapping("/posts/{postId}")
	fun delete(
		@CookieValue(COOKIE_NAME) token: String,
		@PathVariable("postId") postId: String,
	) {
		val input = DeletePostInput(token = token, postId = postId)
		deletePostU.run(input)
	}
}

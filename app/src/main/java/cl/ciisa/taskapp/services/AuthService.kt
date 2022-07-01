package cl.ciisa.taskapp.services

import cl.ciisa.taskapp.models.LoginPayloadDTO
import cl.ciisa.taskapp.models.LoginResponseDTO
import cl.ciisa.taskapp.models.RegisterPayloadDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/auth/local")
    fun login(@Body payload: LoginPayloadDTO): Call<LoginResponseDTO>

    @POST("/api/auth/local/register")
    fun register(@Body payload: RegisterPayloadDTO): Call<Void>
}
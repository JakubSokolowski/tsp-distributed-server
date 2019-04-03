from django.urls import path
from bnb import views

urlpatterns = [
    path('bnb/', views.bnb_job_list),
    path('bnb/<int:pk>/', views.bnb_job_detail),
]
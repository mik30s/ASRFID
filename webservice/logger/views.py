from django.shortcuts import render
from django.http import HttpResponse

# Create your views here.

def index(request):
	return HttpResponse("Logger is up and running!");


def add_user(request):
	return HttpResponse("added")
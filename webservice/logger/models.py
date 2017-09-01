from django.db import models

# Create your models here.

class Subject(models.Model):
	tagId = models.BigIntegerField()

	name = models.CharField(max_length=200)
	
	endTime = models.DateTimeField('start time')
	
	startTime = models.DateTimeField('end time')
	
	location = models.CharField(max_length=200)

	description = models.CharField(max_length=1000)
	

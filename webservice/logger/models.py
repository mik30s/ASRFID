from django.db import models

# Create your models here.

class Subject(models.Model):
	tagId = models.BigIntegerField(primary_key=True)

	name = models.CharField(max_length=200)
	
	endTime = models.DateTimeField('start time', null=True)
	
	startTime = models.DateTimeField('end time', null=True)
	
	location = models.CharField(max_length=200)

	description = models.CharField(max_length=1000)

	def __str__(self):
		return "tag id: {} start: {} end: {}".format(self.tagId, self.startTime, self.endTime)

	

from django.db import models


class BNBJob(models.Model):
    lower_bound = models.IntegerField(default=0)

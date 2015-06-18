# FeatureMiner: Automatic feature extraction and rating from online reviews

This is a project completed in April 2015 for the *Information Retrieval and Data Mining* at UCL.

One most online store, products are rated *as a whole* and given a single rating. On Amazon for example, it takes the form of 1-to-5 stars opinion.

So what do you do when you want to buy something and you see it's rated 3 stars? Well, this is highly uninformative. So you scroll down and check out the reviews.

After reading 3 or 4 of these, you start to get the picture. For a laptop for example, you can summarize "The screen is small, but it's not too heavy. The graphic card is not last generation but the CPU is pretty good." which explains the 3 stars rating.

What if a software could do that for you? This is the purpose of the project.  

## The Algorithm

* Spotting the features

Not every word (or even every noun) mentionned in a review is a feature of the product! We could specify that "screen", "CPU" and so on are characteritics to look for, but then the algorithm would work only for laptops.

Instead, it relies on a dataset containing many reviews from products from the same category. Using a rule mining algorithm, it detects what seem to be the features of this kind of product. This way, features are homegenous across a category, ensuring easy comparison.

This means that it works not matter what the category is! Nothing was entered "by hand": the algorithm is efficient processing the reviews of laptop, cellphones, books, garden shed, etc.

It can even pick up complex characteristics like "battery life" which is composed of two words. A pruning by cofrequency is automatically done to avoid duplicates like "battery" and "battery life" to appear. Finally, a final filtering is done to ensure that words too common do not get listed as features (it would make little sense to have "computer" as characteristic of a computer).


* Rating the features

Now that we now what to look for, we can easily find in which sentence a reviewer discuss a feature. But what is their opinion about it? To find this, I used the Stanford coreNLP library which provides state-of-the-art sentiment analysis. Ideally, the sentiment analyser should be retrained for the specific domain in which it is used, but this necessitates a labelled dataset.

## How to use the library

Note that the code is designed to be used like a library. Using the methods from the `FeatureMining` class, one can build a summary by simply giving a path to the reviews, which should be in a json format. 

This will result in a summary object (from the `Summary` class) which can be further manipulated with the provided methods. 

Here is a minimal example, where `dataPath` is the path to the directory where all the reviews are, and `reviewPath` is the path to the single review we want to summarize:

```
		// the features are computed *once* during initialization
		FeatureMining miner = new FeatureMining(dataPath);

		// The summary for this single review is computed
		Summary summary = miner.mineFeatures(reviewPath);
		
		// The summary can easily be printed
		System.out.println(summary);
		
		// Other reviews could be summarized by 'miner' without recomputing the features
```
This is showcased in the `Example` class. There are plenty of other ways to use the `featureMining` class, for example you can provide yourself an arrayList of features (which you may have saved previously) to avoid the computationally intensive task of extracting them. Check out the javadoc!

Although using `FeatureMining` and `Summary` is the easiest way to use this library, one could also use the classes `FeatureExtractor` to extract the list of features and `FeatureRater` to rate a single review given a list of features. All these classes are documented in the javadoc.

## Results

For the laptop data that we had, the feature extraction yields:

'''
system 
review
pc
keyboard
mouse
internet
drive
memory
software
size
speed
processor
desktop
graphics
speakers
touchpad
performance
games
support
laptops
display
pad
keys
fan
gaming
warranty
life_battery
screen_laptop
laptop_price
touch_screen
'''

Notice the complex features at the end!

Then, the review is parsed and analysed, resulting in Summary object which can be further manipulated and from which structured data can be easily extracted.

Printing the Summary to the console yeilds a raw report looking like this:

```
**### graphics : 1 positive / 1 negative ###**
- I wiped the hard drive, turned off UEFI, and then installed Opensuse 12.2 (12.1 does not have a driver for this new graphics chip).
*--> negative*
- It is also capable of actually playing some games despite it having integrated graphics, including Minecraft at nearly maximum settings.
*--> positive*
**### processor : 1 positive / 2 negative ###**
- Since then I have purchased over 20 of these for clients, all of which love this laptop and can't believe the price / quality.The Intel I3 processor with 4gb of RAM really make this a snappy laptop.
*--> negative*
- The processor is fast.
*--> positive*
- While using a word processor and while browsing the internet, the zoom goes crazy!
*--> negative*
**### speed : 1 positive / 0 negative ###**
- The smaller size and light weight will allow him to easily carry it around while not loosing any processing speed and memory.
*--> positive*
```

This allows a user to understand at a glance what the strengths/weaknesses of the product are. Obviously, the data printed here his programmatically accessible in a structured way in the Summary object.

 
 
